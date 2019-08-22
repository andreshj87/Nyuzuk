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

    suspend fun getTopArticles(freshData: Boolean): Either<Failure, List<ArticleRemote>> {
        if (freshData) {
            topArticlesPaginator.invalidate()
        }
        return if (topArticlesPaginator.requestMore()) {
            val requestConfig = topArticlesPaginator.getRequestConfig()
            articleApiService.getTopArticles(requestConfig.page, requestConfig.perPage)
                .processPaginatedResponse(topArticlesPaginator)
                .toEither()
        } else {
            Either.Left(Failure.ApiError)
        }
    }

    suspend fun searchArticles(query: String, freshData: Boolean): Either<Failure, List<ArticleRemote>> {
        if (freshData) {
            searchArticlesPaginator.invalidate()
        }
        return if (searchArticlesPaginator.requestMore()) {
            val requestConfig = searchArticlesPaginator.getRequestConfig()
            articleApiService.searchArticles(query, requestConfig.page, requestConfig.perPage)
                .processPaginatedResponse(searchArticlesPaginator)
                .toEither()
        } else {
            Either.Left(Failure.ApiError)
        }
    }
}