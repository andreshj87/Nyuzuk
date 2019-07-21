package com.andres.nyuzuk.domain.usecase

import com.andres.nyuzuk.domain.Either
import com.andres.nyuzuk.domain.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> {
    abstract suspend fun execute(params: Params): Either<Failure, Type>

    operator fun invoke(scope: CoroutineScope, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val backgroundJob = scope.async { execute(None() as Params) }
        scope.launch { onResult(backgroundJob.await()) }
    }

    operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val backgroundJob = scope.async { execute(params) }
        scope.launch { onResult(backgroundJob.await()) }
    }

    class None
}