package com.andres.nyuzuk.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface PostExecutionThread {
    fun getScheduler(): CoroutineDispatcher
}