package com.andres.nyuzuk.data.extension

import arrow.core.Either
import com.andres.nyuzuk.data.datasource.remote.api.ApiPaginator
import com.andres.nyuzuk.data.entity.remote.ArticleRemote
import com.andres.nyuzuk.data.entity.remote.ArticleResponse
import com.andres.nyuzuk.data.entity.remote.PaginatedApiResponse
import com.andres.nyuzuk.domain.Failure
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.Locale
import java.util.Date

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

fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}