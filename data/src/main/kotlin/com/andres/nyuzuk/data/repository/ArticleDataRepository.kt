package com.andres.nyuzuk.data.repository

import arrow.core.Either
import com.andres.nyuzuk.data.datasource.local.ArticleLocalDataSource
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.flow

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleMapper: ArticleMapper
) : ArticleRepository {
    override suspend fun getTopArticles(invalidate: Boolean, fetchMore: Boolean) = flow {
        if (!invalidate && !fetchMore) {
            val topArticlesLocal = articleMapper.mapFromLocal(articleLocalDataSource.getTopArticles())
            emit(Either.Right(topArticlesLocal))
        } else if (invalidate) {
            articleLocalDataSource.invalidateTopArticles()
        }
        val topArticlesRemote = articleRemoteDataSource.getTopArticles(invalidate)
        if (topArticlesRemote.isLeft()) {
            emit(Either.Left(Failure.NetworkConnection))
        } else {
            val topArticles = topArticlesRemote.map {
                val topArticles = articleMapper.mapFromRemote(it)
                articleLocalDataSource.save(articleMapper.mapToLocal(topArticles, isTop = true))
                topArticles
            }
            emit(topArticles)
        }
    }

    override suspend fun searchArticles(query: String, invalidate: Boolean, fetchMore: Boolean) = flow {
        if (!fetchMore) {
            val articlesSearch = articleMapper.mapFromLocal(articleLocalDataSource.getArticlesSearch(query))
            emit(Either.Right(articlesSearch))
        }
        if (invalidate) {
            articleLocalDataSource.invalidateArticlesSearch(query)
        }
        val articlesSearchRemote = articleRemoteDataSource.searchArticles(query, invalidate)
        if (articlesSearchRemote.isLeft()) {
            emit(Either.Left(Failure.NetworkConnection))
        } else {
            val articlesSearch = articlesSearchRemote.map {
                val articlesSearch = articleMapper.mapFromRemote(it)
                articleLocalDataSource.save(articleMapper.mapToLocal(articlesSearch, searchQuery = query))
                articlesSearch
            }
            emit(articlesSearch)
        }
    }
}