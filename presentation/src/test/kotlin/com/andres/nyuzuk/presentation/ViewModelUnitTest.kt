package com.andres.nyuzuk.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.BaseViewState
import org.junit.Rule
import org.junit.rules.TestRule

abstract class ViewModelUnitTest<S : BaseViewState, M : BaseViewModel<S>> : UnitTest() {
    lateinit var viewModel: M

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    fun getViewStateValue() = viewModel.viewState.value
}