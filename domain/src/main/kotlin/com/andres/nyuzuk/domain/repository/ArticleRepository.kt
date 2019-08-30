package com.andres.nyuzuk.domain.repository

import arrow.core.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getTopArticles(invalidate: Boolean, fetchMore: Boolean): Flow<Either<Failure, List<Article>>>
    suspend fun searchArticles(query: String, invalidate: Boolean, fetchMore: Boolean): Flow<Either<Failure, List<Article>>>
}