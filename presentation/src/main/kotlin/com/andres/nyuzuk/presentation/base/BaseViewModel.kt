package com.andres.nyuzuk.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<ViewState : BaseViewState>: ViewModel() {
    val viewState = MutableLiveData<ViewState>()

    init {
        this.initViewState()
    }

    abstract fun initViewState()

    fun getViewState() = viewState.value!!

    abstract fun onViewReady()
}