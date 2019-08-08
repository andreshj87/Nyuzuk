package com.andres.nyuzuk.data.remote

import com.andres.nyuzuk.data.entity.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("/v2/top-headlines?country=us")
    fun getTopHeadlines(): Call<ArticleResponse>

    @GET("/v2/everything")
    fun searchArticles(@Query("q") query: String): Call<ArticleResponse>
}