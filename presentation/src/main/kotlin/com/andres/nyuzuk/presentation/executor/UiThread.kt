package com.andres.nyuzuk.presentation.executor

import com.andres.nyuzuk.domain.executor.PostExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UiThread : PostExecutionThread {
    override fun getScheduler(): CoroutineDispatcher = Dispatchers.Main
}