package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.repository.ArticleRepository

class GetTopArticles(
    private val articleRepository: ArticleRepository
) : UseCase<List<Article>, GetTopArticles.Params>() {
    override suspend fun execute(params: Params): Either<Failure, List<Article>> {
        return articleRepository.getTopArticles(params.invalidating)
    }

    data class Params(val invalidating: Boolean = false)
}