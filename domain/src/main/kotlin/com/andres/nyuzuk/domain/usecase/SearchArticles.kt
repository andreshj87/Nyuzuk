package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.repository.ArticleRepository

class SearchArticles(
    private val articleRepository: ArticleRepository
) : UseCase<List<Article>, SearchArticles.Params>() {
    override suspend fun execute(params: Params): Either<Failure, List<Article>> {
        return articleRepository.searchArticles(params.query, params.invalidating)
    }

    data class Params(val query: String, val invalidating: Boolean = false)
}