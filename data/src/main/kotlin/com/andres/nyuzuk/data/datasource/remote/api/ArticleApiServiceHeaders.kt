package com.andres.nyuzuk.data.datasource.remote.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ArticleApiServiceHeaders(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestWithHeaders = original.newBuilder()
            .header("X-Api-Key", apiKey)
            .build()
        return chain.proceed(requestWithHeaders)
    }
}