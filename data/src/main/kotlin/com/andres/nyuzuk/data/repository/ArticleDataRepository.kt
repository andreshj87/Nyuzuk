package com.andres.nyuzuk.data.repository

import arrow.core.Either
import arrow.core.flatMap
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.repository.ArticleRepository

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleMapper: ArticleMapper
) : ArticleRepository {
    override suspend fun getTopArticles(freshData: Boolean) =
        articleRemoteDataSource.getTopArticles(freshData).flatMap { Either.Right(articleMapper.map(it)) }

    override suspend fun searchArticles(query: String, freshData: Boolean) =
        articleRemoteDataSource.searchArticles(query, freshData).flatMap { Either.Right(articleMapper.map(it)) }
}