package com.andres.nyuzuk.presentation.features.main

import com.andres.nyuzuk.presentation.base.BaseViewModel

class MainViewModel : BaseViewModel<MainViewState>() {
    override fun initViewState() {
        viewState.value = MainViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(showSearchButton = true)
    }

    fun onSearchClick() {
        // TODO
    }
}