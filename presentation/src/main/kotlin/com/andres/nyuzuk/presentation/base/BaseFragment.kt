package com.andres.nyuzuk.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<ViewState : BaseViewState, ViewModel : BaseViewModel<ViewState>>(viewModelClass: KClass<ViewModel>) :
    Fragment() {
    protected val viewModel: ViewModel by viewModel(viewModelClass)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.viewState.observe(this, Observer {
            render(it)
        })
        viewModel.onViewReady()
    }

    abstract fun getLayoutResource(): Int

    abstract fun render(viewState: ViewState)

    open fun setupUi() { }
}