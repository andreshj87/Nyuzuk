package com.andres.nyuzuk.data.datasource

import arrow.core.Either
import com.andres.nyuzuk.data.entity.ArticleRemote
import com.andres.nyuzuk.data.extension.toEither
import com.andres.nyuzuk.data.remote.ArticleApiService
import com.andres.nyuzuk.domain.Failure

class ArticleRemoteDataSource(
    private val articleApiService: ArticleApiService
) {
    suspend fun getTopArticles(): Either<Failure, List<ArticleRemote>> {
        return articleApiService.getTopArticles()
            .toEither()
    }

    suspend fun searchArticles(query: String): Either<Failure, List<ArticleRemote>> {
        return articleApiService.searchArticles(query)
            .toEither()
    }
}