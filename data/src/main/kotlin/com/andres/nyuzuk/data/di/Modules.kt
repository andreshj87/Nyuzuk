package com.andres.nyuzuk.data.di

import com.andres.nyuzuk.data.BuildConfig
import com.andres.nyuzuk.data.datasource.local.ArticleLocalDataSource
import com.andres.nyuzuk.data.datasource.local.database.ArticleDatabase
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.datasource.remote.api.ArticleApiService
import com.andres.nyuzuk.data.datasource.remote.api.ArticleApiServiceHeaders
import com.andres.nyuzuk.data.repository.ArticleDataRepository
import com.andres.nyuzuk.domain.repository.ArticleRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val repositoryModule: Module = module {
    single {
        ArticleDataRepository(get(), get(), get()) as ArticleRepository
    }
}

val dataSourceModule: Module = module {
    single {
        ArticleRemoteDataSource(get())
    }
    single {
        ArticleLocalDataSource(get())
    }
}

val networkModule: Module = module {
    single {
        provideArticleApiService(get())
    }

    single {
        provideRetrofit(provideApiUrl(), get())
    }

    single {
        provideOkHttpClient()
    }
}

val databaseModule: Module = module {
    single {
        ArticleDatabase(androidContext()).ArticleDao()
    }
}

fun provideArticleApiService(retrofit: Retrofit): ArticleApiService {
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

fun provideOkHttpClientHeaders() =
    ArticleApiServiceHeaders(provideApiKey())

fun provideApiUrl() = "https://newsapi.org/v2/"

fun provideApiKey() = BuildConfig.API_KEY