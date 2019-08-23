package com.andres.nyuzuk.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<ViewState : BaseViewState>: ViewModel() {
    @JvmField val viewState = MutableLiveData<ViewState>()

    init {
        initViewState()
    }

    abstract fun initViewState()

    fun getViewState() = viewState.value!!

    abstract fun onViewReady()
}