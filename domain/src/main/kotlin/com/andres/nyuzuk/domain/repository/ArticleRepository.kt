package com.andres.nyuzuk.domain.repository

import com.andres.nyuzuk.domain.datasource.ArticleRemoteDataSource

class ArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) {
    fun getTopHeadlines() = articleRemoteDataSource.getTopHeadlines()

    fun searchArticles(query: String) = articleRemoteDataSource.searchArticles(query)
}