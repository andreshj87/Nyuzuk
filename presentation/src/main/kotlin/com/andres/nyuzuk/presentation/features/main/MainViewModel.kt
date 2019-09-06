package com.andres.nyuzuk.presentation.features.main

import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.tools.Navigator

class MainViewModel(
    private val navigator: Navigator
) : BaseViewModel<MainViewState>() {
    override fun initViewState() {
        viewState.value = MainViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(showSearchButton = true)
    }

    fun onSearchClick() {
        navigator.navigateToSearch()
    }
}