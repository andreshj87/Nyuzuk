package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.repository.ArticleRepository

class GetTopArticles(
    private val articleRepository: ArticleRepository
) : UseCase<List<Article>, UseCase.None>() {
    override suspend fun execute(params: None): Either<Failure, List<Article>> {
        return articleRepository.getTopArticles()
    }
}