package com.andres.nyuzuk.data.entity

import com.squareup.moshi.Json

data class ArticleRemote(
    @Json(name = "source") val publisher: PublisherRemote,
    @Json(name = "author") val author: String?,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "url") val url: String,
    @Json(name = "urlToImage") val imageUrl: String,
    @Json(name = "content") val content: String?
)