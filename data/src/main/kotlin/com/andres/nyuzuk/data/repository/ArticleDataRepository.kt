package com.andres.nyuzuk.data.repository

import com.andres.nyuzuk.data.datasource.ArticleRemoteDataSource
import com.andres.nyuzuk.domain.repository.ArticleRepository

class ArticleDataRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {
    override suspend fun getTopArticles() = articleRemoteDataSource.getTopArticles()

    override suspend fun searchArticles(query: String) = articleRemoteDataSource.searchArticles(query)
}