package com.andres.nyuzuk.data.datasource.local

import com.andres.nyuzuk.data.datasource.local.database.ArticleDao
import com.andres.nyuzuk.data.entity.local.ArticleEntity

class ArticleLocalDataSource(
    private val articleDao: ArticleDao
) {
    fun save(articlesEntity: List<ArticleEntity>) = articleDao.save(articlesEntity)

    fun getTopArticles() = articleDao.getTopArticles()

    fun invalidateTopArticles() = articleDao.invalidateTopArticles()

    fun getArticlesSearch(query: String) = articleDao.getArticlesSearch(query)

    fun invalidateArticlesSearch(query: String) = articleDao.invalidateArticlesSearch(query)
}