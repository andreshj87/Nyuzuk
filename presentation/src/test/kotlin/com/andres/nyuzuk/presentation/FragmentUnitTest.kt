package com.andres.nyuzuk.presentation

import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import com.andres.nyuzuk.presentation.base.BaseFragment
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.BaseViewState

abstract class FragmentUnitTest<S : BaseViewState, M : BaseViewModel<S>, T : BaseFragment<S, M>> : RobolectricUnitTest<T>() {
    protected lateinit var fragmentScenario: FragmentScenario<T>

    override fun onSetup() {
        super.onSetup()
        fragmentScenario = FragmentScenario.launch(getClassUnderTest())
        fragmentScenario.moveToState(Lifecycle.State.RESUMED)
    }

    abstract fun getClassUnderTest(): Class<T>
}