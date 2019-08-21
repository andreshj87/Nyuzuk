package com.andres.nyuzuk.data.extension

import arrow.core.Either
import com.andres.nyuzuk.data.entity.ArticleRemote
import com.andres.nyuzuk.data.entity.ArticleResponse
import com.andres.nyuzuk.domain.Failure
import retrofit2.Response

fun Response<ArticleResponse>.toEither(): Either<Failure, List<ArticleRemote>> {
    return Either.cond(
        this.isSuccessful && this.body() != null,
        { this.body()!!.articles },
        { Failure.ApiError })
}