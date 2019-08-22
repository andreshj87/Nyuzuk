package com.andres.nyuzuk.data.entity.remote

import com.squareup.moshi.Json

data class ArticleResponse(
    val status: String,
    @JvmField val totalResults: Int,
    val articles: List<ArticleRemote>,
    @Json(name = "code") val errorCode: String,
    @Json(name = "message") val errorMessage: String
): PaginatedApiResponse {
    override fun getTotalResults(): Int {
        return totalResults
    }
}