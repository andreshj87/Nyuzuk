package com.andres.nyuzuk.domain.entity

data class Article(
    val title: String,
    val description: String,
    val content: String?,
    val author: String?,
    val publisher: Publisher,
    val imageUrl: String,
    val url: String
)