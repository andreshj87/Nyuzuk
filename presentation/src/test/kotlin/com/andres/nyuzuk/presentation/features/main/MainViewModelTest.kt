package com.andres.nyuzuk.presentation.features.main

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.ViewModelUnitTest
import org.junit.Test

class MainViewModelTest: ViewModelUnitTest<MainViewState, MainViewModel>() {
    override fun onSetup() {
        viewModel = MainViewModel()
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
}