package com.andres.nyuzuk.data.datasource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andres.nyuzuk.data.entity.local.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(articlesEntity: List<ArticleEntity>)

    @Query("SELECT * FROM article WHERE isTop = 1 ORDER BY publishedAt DESC")
    fun getTopArticles(): List<ArticleEntity>

    @Query("DELETE FROM article WHERE isTop = 1")
    fun invalidateTopArticles()

    @Query("SELECT * FROM article WHERE searchQuery LIKE :query ORDER BY publishedAt DESC")
    fun getArticlesSearch(query: String): List<ArticleEntity>

    @Query("DELETE FROM article WHERE searchQuery LIKE :query")
    fun invalidateArticlesSearch(query: String)
}