package com.andres.nyuzuk.presentation

import android.app.Application
import com.andres.nyuzuk.presentation.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startDependencyInjection()
    }

    private fun startDependencyInjection() {
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                applicationModule
            )
        }
    }
}