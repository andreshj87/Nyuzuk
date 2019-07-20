package com.andres.nyuzuk.data.remote

import com.andres.nyuzuk.data.entity.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2"
    }

    @GET("$BASE_URL/top-headlines")
    fun getTopHeadlines(): Call<ArticleResponse>

    @GET("$BASE_URL/everything")
    fun searchArticles(@Query("q") query: String)
}