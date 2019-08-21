package com.andres.nyuzuk.data.datasource

import arrow.core.Either
import com.andres.nyuzuk.data.entity.ArticleRemote
import com.andres.nyuzuk.data.entity.ArticleResponse
import com.andres.nyuzuk.data.remote.ArticleApiService
import com.andres.nyuzuk.domain.Failure
import retrofit2.Response

class ArticleRemoteDataSource(
    private val articleApiService: ArticleApiService
) {
    suspend fun getTopArticles(): Either<Failure, List<ArticleRemote>> {
        return fromResponse(articleApiService.getTopArticles())
    }

    suspend fun searchArticles(query: String): Either<Failure, List<ArticleRemote>> {
        return fromResponse(articleApiService.searchArticles(query))
    }

    private fun fromResponse(response: Response<ArticleResponse>): Either<Failure, List<ArticleRemote>> {
        return Either.cond(
            response.isSuccessful && response.body() != null,
            { response.body()!!.articles },
            { Failure.ApiError })
    }
}