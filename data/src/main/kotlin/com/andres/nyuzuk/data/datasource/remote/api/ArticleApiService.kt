package com.andres.nyuzuk.data.datasource.remote.api

import com.andres.nyuzuk.data.entity.remote.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("top-headlines?country=us")
    suspend fun getTopArticles(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Response<ArticleResponse>

    @GET("everything")
    suspend fun searchArticles(@Query("q") query: String): Response<ArticleResponse>
}