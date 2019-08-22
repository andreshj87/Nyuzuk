package com.andres.nyuzuk.data.extension

import arrow.core.Either
import com.andres.nyuzuk.data.datasource.remote.api.ApiPaginator
import com.andres.nyuzuk.data.entity.remote.ArticleRemote
import com.andres.nyuzuk.data.entity.remote.ArticleResponse
import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import com.andres.nyuzuk.domain.Failure
import retrofit2.Response

fun Response<ArticleResponse>.processPaginatedResponse(apiPaginator: ApiPaginator): Response<ArticleResponse> {
    apiPaginator.processResponse(this.body() as PaginatedApiResponse)
    return this
}

fun Response<ArticleResponse>.toEither(): Either<Failure, List<ArticleRemote>> {
    return Either.cond(
        this.isSuccessful && this.body() != null,
        { this.body()!!.articles },
        { Failure.ApiError })
}