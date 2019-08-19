package com.andres.nyuzuk.domain.repository

import com.andres.nyuzuk.domain.datasource.ArticleRemoteDataSource

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) {
    suspend fun getTopHeadlines() = articleRemoteDataSource.getTopHeadlines()

    suspend fun searchArticles(query: String) = articleRemoteDataSource.searchArticles(query)
}