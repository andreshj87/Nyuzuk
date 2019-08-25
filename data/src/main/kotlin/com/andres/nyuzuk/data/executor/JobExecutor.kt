package com.andres.nyuzuk.data.executor

import com.andres.nyuzuk.domain.executor.ThreadExecutor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class JobExecutor : ThreadExecutor {
    override fun getScheduler(): CoroutineDispatcher = Dispatchers.IO
}