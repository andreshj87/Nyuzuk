package com.andres.nyuzuk.domain.repository

import com.andres.nyuzuk.domain.datasource.ArticleRemoteDataSource

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) {
    suspend fun getTopArticles() = articleRemoteDataSource.getTopArticles()

    suspend fun searchArticles(query: String) = articleRemoteDataSource.searchArticles(query)
}