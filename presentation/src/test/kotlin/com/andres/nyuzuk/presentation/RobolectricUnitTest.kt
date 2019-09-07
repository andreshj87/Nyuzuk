package com.andres.nyuzuk.presentation

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.BaseViewState
import org.junit.After
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
abstract class RobolectricUnitTest<S : BaseViewState, M : BaseViewModel<S>, T : BaseActivity<S, M>> : UnitTest() {
    protected lateinit var activityScenario: ActivityScenario<T>

    override fun onSetup() {
        setupMocking()
        setupKoin()
        activityScenario = ActivityScenario.launch(getClassUnderTest())
        activityScenario.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    abstract fun setupMocking()

    fun setupKoin() {
        stopKoin()
        startKoin {
            modules(getModuleMock())
        }
    }

    abstract fun getModuleMock(): Module

    abstract fun getClassUnderTest(): Class<T>
}