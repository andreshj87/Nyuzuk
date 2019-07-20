package com.andres.nyuzuk.domain.datasource

interface ArticleRemoteDataSource {
    fun getTopHeadlines()
    fun searchArticles(query: String)
}