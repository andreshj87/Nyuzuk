package com.andres.nyuzuk.presentation

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
abstract class RobolectricUnitTest<T> : UnitTest() {
    override fun onSetup() {
        setupMocking()
        setupKoin()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    fun getContext(): Context = ApplicationProvider.getApplicationContext()

    abstract fun setupMocking()

    fun setupKoin() {
        stopKoin()
        startKoin {
            modules(getModuleMock())
        }
    }

    abstract fun getModuleMock(): Module
}