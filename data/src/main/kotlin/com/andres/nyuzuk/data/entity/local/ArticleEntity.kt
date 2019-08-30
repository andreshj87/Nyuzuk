package com.andres.nyuzuk.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey val url: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "publisher") val publisher: PublisherEntity?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "isTop") val isTop: Boolean = false,
    @ColumnInfo(name = "searchQuery") val searchQuery: String = "",
    @ColumnInfo(name = "publishedAt") val publishedAt: Date?
)