package com.andres.nyuzuk.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.BaseViewState
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class InstrumentationTest<S : BaseViewState, M : BaseViewModel<S>, T : BaseActivity<S, M>> {
    private lateinit var activityScenario: ActivityScenario<T>

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(getClassUnderTest())
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
        activityScenario.close()
    }

    abstract fun getClassUnderTest(): Class<T>
}