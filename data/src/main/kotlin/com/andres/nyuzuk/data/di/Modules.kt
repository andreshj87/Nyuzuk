package com.andres.nyuzuk.data.di

import com.andres.nyuzuk.data.BuildConfig
import com.andres.nyuzuk.data.datasource.ArticleApiDataSource
import com.andres.nyuzuk.data.remote.ArticleApiService
import com.andres.nyuzuk.data.remote.ArticleApiServiceHeaders
import com.andres.nyuzuk.domain.datasource.ArticleRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataSourceModule: Module = module {
    single {
        ArticleApiDataSource(get(), get()) as ArticleRemoteDataSource
    }
}

val networkModule: Module = module {
    single {
        provideApiService(get())
    }

    single {
        provideRetrofit(provideApiUrl(), get())
    }

    single {
        provideOkHttpClient()
    }
}

fun provideApiService(retrofit: Retrofit): ArticleApiService {
    return retrofit.create(ArticleApiService::class.java)
}

fun provideRetrofit(apiUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    okHttpClientBuilder.addInterceptor(provideOkHttpClientHeaders())
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
    }
    return okHttpClientBuilder.build()
}

fun provideOkHttpClientHeaders() = ArticleApiServiceHeaders(provideApiKey())

fun provideApiUrl() = "https://newsapi.org/v2/"

fun provideApiKey() = BuildConfig.API_KEY