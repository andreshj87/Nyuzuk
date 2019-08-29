package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.executor.PostExecutionThread
import com.andres.nyuzuk.domain.executor.ThreadExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) {
    abstract suspend fun execute(params: Params): Flow<Either<Failure, Type>>

    operator fun invoke(scope: CoroutineScope, params: Params = None() as Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        scope.launch(postExecutionThread.getScheduler()) {
            execute(params)
                .flowOn(threadExecutor.getScheduler())
                .collect {
                    onResult(it)
                }
        }
    }

    class None
}