package com.andres.nyuzuk.presentation.features.search

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.ActivityUnitTest
import com.andres.nyuzuk.presentation.base.ErrorDialog
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import com.andres.nyuzuk.presentation.dummyfactory.ErrorDummyFactory
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.extension.isVisible
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.activity_search_articles.layout_initial
import kotlinx.android.synthetic.main.activity_search_articles.recyclerview_search_articles
import kotlinx.android.synthetic.main.view_empty.layout_empty
import kotlinx.android.synthetic.main.view_loading.view_loading
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.Shadows

class ArticleSearchActivityTest: ActivityUnitTest<ArticleSearchViewState, ArticleSearchViewModel, ArticleSearchActivity>() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()
    private val AMOUNT_OF_ELEMENTS = 30
    private val SOME_SEARCH_QUERY = "technology"
    private val SOME_ERROR_UI = ErrorDummyFactory.createErrorUi()

    @Mock private lateinit var viewModelMock: ArticleSearchViewModel
    @Mock private lateinit var viewStateLiveDataMock: MutableLiveData<ArticleSearchViewState>
    @Mock private lateinit var imageLoaderMock: ImageLoader
    @Mock private lateinit var errorDialogMock: ErrorDialog
    private val viewStateObserverCaptor = argumentCaptor<Observer<ArticleSearchViewState>>()
    private val errorDialogDismissListenerCaptor = argumentCaptor<() -> Any>()

    override fun onSetup() {
        super.onSetup()
        verify(viewStateLiveDataMock).observe(ArgumentMatchers.any(LifecycleOwner::class.java), viewStateObserverCaptor.capture())
    }

    override fun getIntent() = ArticleSearchActivity.makeIntent(getContext())

    override fun setupMocking() {
        `when`(viewModelMock.viewState).thenReturn(viewStateLiveDataMock)
    }

    override fun getModuleMock() = module {
        viewModel {
            viewModelMock
        }
        single {
            imageLoaderMock
        }
        single {
            errorDialogMock
        }
    }

    @Test
    fun `should make the proper intent`() {
        activityScenario.onActivity {
            val intent = ArticleSearchActivity.makeIntent(it)

            assertThat(intent).isNotNull()
            assertThat(intent.component.className).isEqualTo(ArticleSearchActivity::class.java.name)
        }
    }

    @Test
    fun `should get the proper layout resource`() {
        val layoutResourceExpected = R.layout.activity_search_articles

        activityScenario.onActivity {
            val layoutResource = it.getLayoutResource()

            assertThat(layoutResource).isEqualTo(layoutResourceExpected)
        }
    }

    @Test
    fun `should title be set when created`() {
        val titleExpected = getContext().getString(R.string.search_articles_title)

        activityScenario.onActivity {
            assertThat(it.title).isEqualTo(titleExpected)
            it.recyclerview_search_articles.itemAnimator
        }
    }

    @Test
    fun `should be null itemAnimator in recyclerview when created`() {
        activityScenario.onActivity {
            val recyclerView = it.recyclerview_search_articles
            assertThat(recyclerView.itemAnimator).isNull()
        }
    }

    @Test
    fun `should notify viewModel onSearchClick when enter query and submit searchView`() {
        activityScenario.onActivity {
            val shadowActivity = Shadows.shadowOf(it)
            val searchMenuItem = shadowActivity.optionsMenu.findItem(R.id.action_search)
            val searchView = searchMenuItem.actionView as SearchView

            searchView.setQuery(SOME_SEARCH_QUERY, true)

            verify(viewModelMock).onSearchClick(eq(SOME_SEARCH_QUERY))
        }
    }

    @Test
    fun `should notify viewModel onSearchClose when collapse searchView menuItem`() {
        activityScenario.onActivity {
            val shadowActivity = Shadows.shadowOf(it)
            val searchMenuItem = shadowActivity.optionsMenu.findItem(R.id.action_search)

            searchMenuItem.collapseActionView()

            verify(viewModelMock).onSearchClose()
        }
    }

    @Test
    fun `should show view loading when set as true in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isLoading = true))

        activityScenario.onActivity {
            assertThat(it.view_loading.isVisible).isTrue()
        }
    }

    @Test
    fun `should hide view loading when set as false in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isLoading = false))

        activityScenario.onActivity {
            assertThat(it.view_loading.isVisible).isFalse()
        }
    }

    @Test
    fun `should show layout initial when set as true in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isInitial = true))

        activityScenario.onActivity {
            assertThat(it.layout_initial.isVisible).isTrue()
        }
    }

    @Test
    fun `should hide layout initial when set as false in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isInitial = false))

        activityScenario.onActivity {
            assertThat(it.layout_initial.isVisible).isFalse()
        }
    }

    @Test
    fun `should show layout empty when set as true in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isEmpty = true))

        activityScenario.onActivity {
            assertThat(it.layout_empty.isVisible).isTrue()
        }
    }

    @Test
    fun `should hide layout empty when set as false in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isEmpty = false))

        activityScenario.onActivity {
            assertThat(it.layout_empty.isVisible).isFalse()
        }
    }

    @Test
    fun `should show errorDialog when set as true in viewSate and also valid errorUi`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isError = true, errorUi = SOME_ERROR_UI))

        activityScenario.onActivity {
            verify(errorDialogMock).show(eq(it), eq(SOME_ERROR_UI), errorDialogDismissListenerCaptor.capture())
        }
    }

    @Test
    fun `should notify viewModel onErrorDialogDismiss when dismiss errorDialog`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(isError = true, errorUi = SOME_ERROR_UI))

        activityScenario.onActivity {
            verify(errorDialogMock).show(eq(it), eq(SOME_ERROR_UI), errorDialogDismissListenerCaptor.capture())

            errorDialogDismissListenerCaptor.firstValue.invoke()

            verify(viewModelMock).onErrorDialogDismiss()
        }
    }

    @Test
    fun `should be visible recyclerview when non empty foundArticlesUi in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(foundArticlesUi = getListOfArticlesUi()))

        activityScenario.onActivity {
            assertThat(it.recyclerview_search_articles.isVisible()).isTrue()
        }
    }

    @Test
    fun `should be invisible recyclerview when empty foundArticlesUi in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(invalidateList = true, foundArticlesUi = emptyList()))

        activityScenario.onActivity {
            assertThat(it.recyclerview_search_articles.isVisible()).isFalse()
        }
    }

    @Test
    fun `should notify viewModel onLoadMore when scrolling to the bottom of the recyclerview`() {
        val listOfArticlesUi = getListOfArticlesUi()

        viewStateObserverCaptor.firstValue.onChanged(ArticleSearchViewState(invalidateList = false, foundArticlesUi = listOfArticlesUi))

        activityScenario.onActivity {
            it.recyclerview_search_articles.scrollToPosition(AMOUNT_OF_ELEMENTS - 1)

            verify(viewModelMock).onLoadMore()
        }
    }

    private fun getListOfArticlesUi(): List<ArticleUi> {
        val articlesUi = mutableListOf<ArticleUi>()
        repeat(AMOUNT_OF_ELEMENTS) {
            articlesUi.add(SOME_ARTICLE_UI)
        }
        return articlesUi
    }
}