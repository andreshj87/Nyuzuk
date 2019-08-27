package com.andres.nyuzuk.data.entity.remote

import com.squareup.moshi.Json

data class ArticleRemote(
    @Json(name = "source") val publisher: PublisherRemote,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val content: String?
)