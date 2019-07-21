package com.andres.nyuzuk.domain.datasource

import com.andres.nyuzuk.domain.Either
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article

interface ArticleRemoteDataSource {
    fun getTopHeadlines(): Either<Failure, List<Article>>
    fun searchArticles(query: String): Either<Failure, List<Article>>
}