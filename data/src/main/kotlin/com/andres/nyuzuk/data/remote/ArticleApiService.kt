package com.andres.nyuzuk.data.remote

import com.andres.nyuzuk.data.entity.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    companion object {
        private const val API_URL = "https://newsapi.org/v2"
    }

    @GET("$API_URL/top-headlines")
    fun getTopHeadlines(): Call<ArticleResponse>

    @GET("$API_URL/everything")
    fun searchArticles(@Query("q") query: String): Call<ArticleResponse>
}