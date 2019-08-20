package com.andres.nyuzuk.data.datasource

import com.andres.nyuzuk.data.entity.ArticleResponse
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.data.remote.ArticleApiService
import com.andres.nyuzuk.domain.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.datasource.ArticleRemoteDataSource
import com.andres.nyuzuk.domain.entity.Article
import retrofit2.Response

class ArticleApiDataSource(
    private val articleApiService: ArticleApiService,
    private val articleMapper: ArticleMapper
): ArticleRemoteDataSource {
    override suspend fun getTopArticles(): Either<Failure, List<Article>> {
        return fromResponse(articleApiService.getTopArticles())
    }

    override suspend fun searchArticles(query: String): Either<Failure, List<Article>> {
        return fromResponse(articleApiService.searchArticles(query))
    }

    private fun fromResponse(response: Response<ArticleResponse>): Either<Failure, List<Article>> {
        return if (response.isSuccessful && response.body() != null) {
            val articles = articleMapper.map(response.body()!!.articles)
            Either.Right(articles)
        } else {
            Either.Left(Failure.ApiError)
        }
    }
}