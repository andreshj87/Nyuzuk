package com.andres.nyuzuk.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface ThreadExecutor {
    fun getScheduler(): CoroutineDispatcher
}