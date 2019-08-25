package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.executor.PostExecutionThread
import com.andres.nyuzuk.domain.executor.ThreadExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {
    abstract suspend fun execute(params: Params): Either<Failure, Type>

    operator fun invoke(scope: CoroutineScope, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val backgroundJob = scope.async(threadExecutor.getScheduler()) { execute(None() as Params) }
        scope.launch(postExecutionThread.getScheduler()) { onResult(backgroundJob.await()) }
    }

    operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val backgroundJob = scope.async(threadExecutor.getScheduler()) { execute(params) }
        scope.launch(postExecutionThread.getScheduler()) { onResult(backgroundJob.await()) }
    }

    class None
}