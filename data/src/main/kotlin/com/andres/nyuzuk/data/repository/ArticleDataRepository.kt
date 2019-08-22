package com.andres.nyuzuk.data.repository

import arrow.core.Either
import arrow.core.flatMap
import com.andres.nyuzuk.data.datasource.ArticleRemoteDataSource
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.repository.ArticleRepository

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleMapper: ArticleMapper
) : ArticleRepository {
    private val topArticlesPaginator = Paginator()

    override suspend fun getTopArticles(): Either<Failure, List<Article>> {
        return if (topArticlesPaginator.shouldContinue()) {
            articleRemoteDataSource.getTopArticles()
                .flatMap {
                    topArticlesPaginator.updatePage()
                    return Either.Right(articleMapper.map(it))
                }
        } else {
            Either.Left(Failure.ApiError)
        }

    }

    override suspend fun searchArticles(query: String) =
        articleRemoteDataSource.searchArticles(query).flatMap { Either.Right(articleMapper.map(it)) }
}