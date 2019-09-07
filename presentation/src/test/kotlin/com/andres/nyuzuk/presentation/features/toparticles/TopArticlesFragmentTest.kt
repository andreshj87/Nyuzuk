package com.andres.nyuzuk.presentation.features.toparticles

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.FragmentUnitTest
import com.andres.nyuzuk.presentation.base.ErrorDialog
import com.andres.nyuzuk.presentation.dummyfactory.ErrorDummyFactory
import com.andres.nyuzuk.presentation.extension.isVisible
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.android.synthetic.main.fragment_top_articles.recyclerview_top_articles
import kotlinx.android.synthetic.main.fragment_top_articles.view_swipe_to_refresh
import kotlinx.android.synthetic.main.view_empty.layout_empty
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`

class TopArticlesFragmentTest: FragmentUnitTest<TopArticlesViewState, TopArticlesViewModel, TopArticlesFragment>() {
    private val SOME_ERROR_UI = ErrorDummyFactory.createErrorUi()

    @Mock private lateinit var viewModelMock: TopArticlesViewModel
    @Mock private lateinit var viewStateLiveDataMock: MutableLiveData<TopArticlesViewState>
    @Mock private lateinit var imageLoaderMock: ImageLoader
    @Mock private lateinit var errorDialogMock: ErrorDialog
    private val viewStateObserverCaptor = argumentCaptor<Observer<TopArticlesViewState>>()
    private val errorDialogDismissListenerCaptor = argumentCaptor<() -> Any>()

    override fun onSetup() {
        super.onSetup()
        verify(viewStateLiveDataMock).observe(ArgumentMatchers.any(LifecycleOwner::class.java), viewStateObserverCaptor.capture())
    }

    override fun getClassUnderTest(): Class<TopArticlesFragment> = TopArticlesFragment::class.java

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
    fun `should get the proper instance when newInstance`() {
        assertThat(TopArticlesFragment.newInstance()).isInstanceOf(TopArticlesFragment::class.java)
    }

    @Test
    fun `should get the proper layout resource`() {
        val layoutResourceExpected = R.layout.fragment_top_articles

        fragmentScenario.onFragment {
            val layoutResource = it.getLayoutResource()

            assertThat(layoutResource).isEqualTo(layoutResourceExpected)
        }
    }

    @Test
    fun `should be null itemAnimator in recyclerview when created`() {
        fragmentScenario.onFragment {
            val recyclerView = it.recyclerview_top_articles
            assertThat(recyclerView.itemAnimator).isNull()
        }
    }

    @Test
    fun `should set swipeToRefresh as refreshing when set as true in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isLoading = true))

        fragmentScenario.onFragment {
            assertThat(it.view_swipe_to_refresh.isRefreshing).isTrue()
        }
    }

    @Test
    fun `should set swipeToRefresh as not refreshing when set as false in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isLoading = false))

        fragmentScenario.onFragment {
            assertThat(it.view_swipe_to_refresh.isRefreshing).isFalse()
        }
    }

    @Test
    fun `should set layout empty as visible when isEmpty as true in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isEmpty = true))

        fragmentScenario.onFragment {
            assertThat(it.layout_empty.isVisible()).isTrue()
        }
    }

    @Test
    fun `should set layout empty as not visible when isEmpty as false in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isEmpty = false))

        fragmentScenario.onFragment {
            assertThat(it.layout_empty.isVisible()).isFalse()
        }
    }

    @Test
    fun `should show error when isError as true and valid errorUi in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isError = true, errorUi = SOME_ERROR_UI))

        fragmentScenario.onFragment {
            verify(errorDialogMock).show(eq(it.context as Context), eq(SOME_ERROR_UI), errorDialogDismissListenerCaptor.capture())
        }
    }

    @Test
    fun `should not show error when isError as true and null errorUi in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isError = true, errorUi = null))

        fragmentScenario.onFragment {
            verifyZeroInteractions(errorDialogMock)
        }
    }

    @Test
    fun `should not show error when isError as false and valid errorUi in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isError = false, errorUi = SOME_ERROR_UI))

        fragmentScenario.onFragment {
            verifyZeroInteractions(errorDialogMock)
        }
    }

    @Test
    fun `should not show error when isError as false and null errorUi in viewState`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isError = false, errorUi = null))

        fragmentScenario.onFragment {
            verifyZeroInteractions(errorDialogMock)
        }
    }

    @Test
    fun `should notify viewModel onErrorDialogDismiss when errorDialog dismiss`() {
        viewStateObserverCaptor.firstValue.onChanged(TopArticlesViewState(isError = true, errorUi = SOME_ERROR_UI))
        fragmentScenario.onFragment {
            verify(errorDialogMock).show(any(), any(), errorDialogDismissListenerCaptor.capture())

            errorDialogDismissListenerCaptor.firstValue.invoke()

            verify(viewModelMock).onErrorDialogDismiss()
        }
    }
}