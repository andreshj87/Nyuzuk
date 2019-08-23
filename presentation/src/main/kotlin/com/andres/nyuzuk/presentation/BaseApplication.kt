package com.andres.nyuzuk.presentation

import android.app.Application
import com.andres.nyuzuk.data.di.dataSourceModule
import com.andres.nyuzuk.data.di.networkModule
import com.andres.nyuzuk.data.di.repositoryModule
import com.andres.nyuzuk.domain.di.useCaseModule
import com.andres.nyuzuk.presentation.di.applicationModule
import com.andres.nyuzuk.presentation.di.mapperModule
import com.andres.nyuzuk.presentation.di.toolsModule
import com.andres.nyuzuk.presentation.di.viewModelModule
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
                listOf(
                    applicationModule,
                    viewModelModule,
                    mapperModule,
                    useCaseModule,
                    repositoryModule,
                    dataSourceModule,
                    networkModule,
                    toolsModule
                )
            )
        }
    }
}