package com.andres.nyuzuk.data.repository

import arrow.core.Either
import arrow.core.flatMap
import com.andres.nyuzuk.data.datasource.local.ArticleLocalDataSource
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.repository.ArticleRepository

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleMapper: ArticleMapper
) : ArticleRepository {
    override suspend fun getTopArticles(invalidating: Boolean): Either<Failure, List<Article>> {
        val topArticlesRemote = articleRemoteDataSource.getTopArticles(invalidating)
        return if (topArticlesRemote.isLeft()) {
            val topArticlesLocal = articleLocalDataSource.get()
            Either.Right(articleMapper.mapFromLocal(topArticlesLocal))
        } else {
            topArticlesRemote.map {
                val articles = articleMapper.mapFromRemote(it)
                if (invalidating) {
                    articleLocalDataSource.clearTop()
                }
                articleLocalDataSource.save(articleMapper.mapToLocal(articles, isTop = true))
                articles
            }
        }
    }

    override suspend fun searchArticles(query: String, invalidating: Boolean) =
        articleRemoteDataSource.searchArticles(query, invalidating).flatMap { Either.Right(articleMapper.mapFromRemote(it)) }
}