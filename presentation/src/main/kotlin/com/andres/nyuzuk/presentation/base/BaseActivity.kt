package com.andres.nyuzuk.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<ViewState : BaseViewState, ViewModel : BaseViewModel<ViewState>>(viewModelClass: KClass<ViewModel>) :
    AppCompatActivity() {
    val viewModel: ViewModel by viewModel(viewModelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
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