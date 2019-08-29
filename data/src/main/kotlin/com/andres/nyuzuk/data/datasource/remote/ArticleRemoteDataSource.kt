package com.andres.nyuzuk.data.datasource.remote

import arrow.core.Either
import com.andres.nyuzuk.data.datasource.remote.api.ApiPaginator
import com.andres.nyuzuk.data.datasource.remote.api.ArticleApiService
import com.andres.nyuzuk.data.entity.remote.ArticleRemote
import com.andres.nyuzuk.data.extension.processPaginatedResponse
import com.andres.nyuzuk.data.extension.toEither
import com.andres.nyuzuk.domain.Failure

class ArticleRemoteDataSource(
    private val articleApiService: ArticleApiService
) {
    private val topArticlesPaginator = ApiPaginator()
    private val searchArticlesPaginator = ApiPaginator()

    suspend fun getTopArticles(invalidate: Boolean): Either<Failure, List<ArticleRemote>> {
        return if (topArticlesPaginator.requestMore(invalidate)) {
            val requestConfig = topArticlesPaginator.getRequestConfig()
            articleApiService.getTopArticles(requestConfig.page, requestConfig.perPage)
                .processPaginatedResponse(topArticlesPaginator)
                .toEither()
        } else {
            Either.Left(Failure.EmptyResult)
        }
    }

    suspend fun searchArticles(query: String, invalidate: Boolean): Either<Failure, List<ArticleRemote>> {
        return if (searchArticlesPaginator.requestMore(invalidate)) {
            val requestConfig = searchArticlesPaginator.getRequestConfig()
            articleApiService.searchArticles(query, requestConfig.page, requestConfig.perPage)
                .processPaginatedResponse(searchArticlesPaginator)
                .toEither()
        } else {
            Either.Left(Failure.EmptyResult)
        }
    }
}