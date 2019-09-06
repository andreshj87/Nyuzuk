package com.andres.nyuzuk.presentation.features.main

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.ViewModelUnitTest
import com.andres.nyuzuk.presentation.tools.Navigator
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.mockito.Mock

class MainViewModelTest: ViewModelUnitTest<MainViewState, MainViewModel>() {
    @Mock lateinit var navigatorMock: Navigator

    override fun onSetup() {
        viewModel = MainViewModel(navigatorMock)
    }

    @Test
    fun `should be default MainViewState when init view state`() {
        val mainViewStateExpected = MainViewState()

        viewModel.initViewState()

        assertThat(getViewStateValue()).isEqualTo(mainViewStateExpected)
    }

    @Test
    fun `should be MainViewState with true showSearchButton when view ready`() {
        val mainViewStateExpected = MainViewState(showSearchButton = true)

        viewModel.onViewReady()

        assertThat(getViewStateValue()).isEqualTo(mainViewStateExpected)
    }

    @Test
    fun `should navigate to search when search click`() {
        viewModel.onSearchClick()

        verify(navigatorMock).navigateToSearch()
    }
}