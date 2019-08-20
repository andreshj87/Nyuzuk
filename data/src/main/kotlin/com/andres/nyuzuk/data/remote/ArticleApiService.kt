package com.andres.nyuzuk.data.remote

import com.andres.nyuzuk.data.entity.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("top-headlines?country=us&pageSize=10")
    suspend fun getTopArticles(): Response<ArticleResponse>

    @GET("everything")
    suspend fun searchArticles(@Query("q") query: String): Response<ArticleResponse>
}