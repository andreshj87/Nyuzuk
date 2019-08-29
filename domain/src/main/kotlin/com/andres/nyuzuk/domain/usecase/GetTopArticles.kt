package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.executor.PostExecutionThread
import com.andres.nyuzuk.domain.executor.ThreadExecutor
import com.andres.nyuzuk.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetTopArticles(
    private val articleRepository: ArticleRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase<List<Article>, GetTopArticles.Params>(threadExecutor, postExecutionThread) {
    override suspend fun execute(params: Params): Flow<Either<Failure, List<Article>>> {
        return articleRepository.getTopArticles(params.invalidate, params.fetchMore)
    }

    data class Params(val invalidate: Boolean = false, val fetchMore: Boolean = false)
}