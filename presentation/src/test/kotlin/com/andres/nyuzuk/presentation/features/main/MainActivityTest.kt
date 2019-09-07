package com.andres.nyuzuk.presentation.features.main

import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.ActivityUnitTest
import com.andres.nyuzuk.presentation.extension.isVisible
import com.andres.nyuzuk.presentation.features.toparticles.TopArticlesViewModel
import com.andres.nyuzuk.presentation.features.toparticles.TopArticlesViewState
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import kotlinx.android.synthetic.main.activity_main.fab_search
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MainActivityTest : ActivityUnitTest<MainViewState, MainViewModel, MainActivity>() {
    @Mock private lateinit var mainViewModelMock: MainViewModel
    @Mock private lateinit var mainViewStateLiveDataMock: MutableLiveData<MainViewState>
    @Mock private lateinit var topArticlesViewModelMock: TopArticlesViewModel
    @Mock private lateinit var topArticlesViewStateMock: MutableLiveData<TopArticlesViewState>
    private val mainViewStateObserverCaptor = argumentCaptor<Observer<MainViewState>>()

    override fun onSetup() {
        super.onSetup()
        verify(mainViewStateLiveDataMock).observe(ArgumentMatchers.any(LifecycleOwner::class.java), mainViewStateObserverCaptor.capture())
    }

    override fun setupMocking() {
        `when`(mainViewModelMock.viewState).thenReturn(mainViewStateLiveDataMock)
        `when`(topArticlesViewModelMock.viewState).thenReturn(topArticlesViewStateMock)
    }

    override fun getModuleMock() = module {
        viewModel {
            mainViewModelMock
        }
        viewModel {
            topArticlesViewModelMock
        }
        single {
            Mockito.mock(ImageLoader::class.java)
        }
    }

    override fun getIntent() = Intent(getContext(), MainActivity::class.java)

    @Test
    fun `should get the right layout resource`() {
        activityScenario.onActivity {
            val layoutResourceExpected = R.layout.activity_main

            val layoutResource = it.getLayoutResource()

            assertThat(layoutResource).isEqualTo(layoutResourceExpected)
        }
    }

    @Test
    fun `should notify viewModel when search click`() {
        activityScenario.onActivity {
            it.fab_search.callOnClick()
        }

        verify(mainViewModelMock).onSearchClick()
    }

    @Test
    fun `should show fab_search when true showSearchButton in viewState`() {
        mainViewStateObserverCaptor.firstValue.onChanged(MainViewState(showSearchButton = true))

        activityScenario.onActivity {
            assertThat(it.fab_search.isVisible()).isTrue()
        }
    }

    @Test
    fun `should hide fab_search when false showSearchButton in viewState`() {
        mainViewStateObserverCaptor.firstValue.onChanged(MainViewState(showSearchButton = false))

        activityScenario.onActivity {
            assertThat(it.fab_search.isVisible()).isFalse()
        }
    }
}