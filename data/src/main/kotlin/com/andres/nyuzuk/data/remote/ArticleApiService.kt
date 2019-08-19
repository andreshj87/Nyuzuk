package com.andres.nyuzuk.data.remote

import com.andres.nyuzuk.data.entity.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("/v2/top-headlines?country=us")
    suspend fun getTopHeadlines(): Response<ArticleResponse>

    @GET("/v2/everything")
    suspend fun searchArticles(@Query("q") query: String): Response<ArticleResponse>
}