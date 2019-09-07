package com.andres.nyuzuk.presentation.features.detail

import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.ActivityUnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.android.synthetic.main.activity_article_detail.button_article_see_more
import kotlinx.android.synthetic.main.activity_article_detail.image_article
import kotlinx.android.synthetic.main.activity_article_detail.text_article_author
import kotlinx.android.synthetic.main.activity_article_detail.text_article_body
import kotlinx.android.synthetic.main.activity_article_detail.text_article_title
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ArticleDetailActivityTest: ActivityUnitTest<ArticleDetailViewState, ArticleDetailViewModel, ArticleDetailActivity>() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()

    @Mock private lateinit var viewModelMock: ArticleDetailViewModel
    @Mock private lateinit var viewStateLiveDataMock: MutableLiveData<ArticleDetailViewState>
    @Mock private lateinit var imageLoaderMock: ImageLoader
    private val viewStateObserverCaptor = argumentCaptor<Observer<ArticleDetailViewState>>()

    override fun onSetup() {
        super.onSetup()
        verify(viewStateLiveDataMock).observe(ArgumentMatchers.any(LifecycleOwner::class.java), viewStateObserverCaptor.capture())
    }

    override fun setupMocking() {
        `when`(viewModelMock.viewState).thenReturn(viewStateLiveDataMock)
    }

    override fun getModuleMock(): Module = module {
        viewModel {
            viewModelMock
        }
        single {
            imageLoaderMock
        }
    }

    override fun getIntent() = ArticleDetailActivity.makeIntent(getContext(), SOME_ARTICLE_UI)

    @Test
    fun `should make the proper intent`() {
        activityScenario.onActivity {
            val articleUiExpected = SOME_ARTICLE_UI

            val intent = ArticleDetailActivity.makeIntent(it, SOME_ARTICLE_UI)

            assertThat(intent).isNotNull()
            assertThat(intent.component.className).isEqualTo(ArticleDetailActivity::class.java.name)
            assertThat(intent.hasExtra(ArticleDetailActivity.EXTRA_ARTICLE_UI)).isTrue()
            val articleUi = intent.extras.getParcelable(ArticleDetailActivity.EXTRA_ARTICLE_UI) as ArticleUi
            assertThat(articleUi).isEqualTo(articleUiExpected)
        }
    }

    @Test
    fun `should get the proper layout resource`() {
        val layoutResourceExpected = R.layout.activity_article_detail

        activityScenario.onActivity {
            val layoutResource = it.getLayoutResource()

            assertThat(layoutResource).isEqualTo(layoutResourceExpected)
        }
    }

    @Test
    fun `should notify onArticleLoaded in viewModel when created`() {
        verify(viewModelMock).onArticleLoaded(eq(SOME_ARTICLE_UI))
    }

    @Test
    fun `should title be set when created`() {
        val titleExpected = getContext().getString(R.string.article_detail_title)
        activityScenario.onActivity {
            assertThat(it.title).isEqualTo(titleExpected)
        }
    }

    @Test
    fun `should notify onSeeMoreClick in viewModel when button article see more click`() {
        activityScenario.onActivity {
            it.button_article_see_more.callOnClick()
        }

        verify(viewModelMock).onSeeMoreClick()
    }

    @Test
    fun `should go back when home toolbar button is selected`() {
        val menuItemMock = mock(MenuItem::class.java)
        `when`(menuItemMock.itemId).thenReturn(android.R.id.home)

        activityScenario.onActivity {
            it.onOptionsItemSelected(menuItemMock)

            assertThat(it.isFinishing).isTrue()
        }
    }

    @Test
    fun `should load image when articleUi with valid image set in viewState`() {
        val imageUrlExpected = SOME_ARTICLE_UI.imageUrl as String

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(SOME_ARTICLE_UI))

        activityScenario.onActivity {
            verify(imageLoaderMock).load(eq(imageUrlExpected), eq(it.image_article))
        }
    }

    @Test
    fun `should not load image when articleUi with invalid image set in viewState`() {
        val someArticleUi = SOME_ARTICLE_UI.copy(imageUrl = null)

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(someArticleUi))

        activityScenario.onActivity {
            verifyZeroInteractions(imageLoaderMock)
        }
    }

    @Test
    fun `should set article title when set in viewState`() {
        val titleExpected = SOME_ARTICLE_UI.title

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(SOME_ARTICLE_UI))

        activityScenario.onActivity {
            assertThat(it.text_article_title.text).isEqualTo(titleExpected)
        }
    }

    @Test
    fun `should set article author when set in viewState`() {
        val authorExpected = SOME_ARTICLE_UI.author

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(SOME_ARTICLE_UI))

        activityScenario.onActivity {
            assertThat(it.text_article_author.text).isEqualTo(authorExpected)
        }
    }

    @Test
    fun `should not set article author when invalid author set in viewState`() {
        val someArticleUi = SOME_ARTICLE_UI.copy(author = null)

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(someArticleUi))

        activityScenario.onActivity {
            assertThat(it.text_article_author.text).isEqualTo("")
        }
    }

    @Test
    fun `should set content as body text when valid content in viewState`() {
        val bodyTextExpected = SOME_ARTICLE_UI.content

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(SOME_ARTICLE_UI))

        activityScenario.onActivity {
            assertThat(it.text_article_body.text).isEqualTo(bodyTextExpected)
        }
    }

    @Test
    fun `should set description as body text when invalid content and valid description in viewState`() {
        val someArticleUi = SOME_ARTICLE_UI.copy(content = null)
        val bodyTextExpected = someArticleUi.description

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(someArticleUi))

        activityScenario.onActivity {
            assertThat(it.text_article_body.text).isEqualTo(bodyTextExpected)
        }
    }

    @Test
    fun `should not set body text when invalid content and description in viewState`() {
        val someArticleUi = SOME_ARTICLE_UI.copy(content = null, description = null)

        viewStateObserverCaptor.firstValue.onChanged(ArticleDetailViewState(someArticleUi))

        activityScenario.onActivity {
            assertThat(it.text_article_body.text).isEqualTo("")
        }
    }
}