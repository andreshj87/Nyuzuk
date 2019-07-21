package com.andres.nyuzuk.data.entity

import com.squareup.moshi.Json

data class ArticleResponse(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val total: Int,
    @Json(name = "articles") val articles: List<ArticleRemote>,
    @Json(name = "code") val errorCode: String,
    @Json(name = "message") val errorMessage: String
)