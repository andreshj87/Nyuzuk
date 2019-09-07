package com.andres.nyuzuk.presentation

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.BaseViewState

abstract class ActivityUnitTest<S : BaseViewState, M : BaseViewModel<S>, T : BaseActivity<S, M>>: RobolectricUnitTest<T>() {
    protected lateinit var activityScenario: ActivityScenario<T>

    override fun onSetup() {
        super.onSetup()
        activityScenario = ActivityScenario.launch(getIntent())
        activityScenario.moveToState(Lifecycle.State.RESUMED)
    }

    abstract fun getIntent(): Intent
}