package com.andres.nyuzuk.presentation.base

import com.andres.nyuzuk.domain.entity.Article

class ArticleUiMapper(
    private val publisherUiMapper: PublisherUiMapper
) {
    fun map(articles: List<Article>): List<ArticleUi> {
        return articles.map {
            map(it)
        }
    }

    private fun map(article: Article): ArticleUi {
        return ArticleUi(
            article.title,
            article.description,
            article.content,
            article.author,
            publisherUiMapper.map(article.publisher),
            article.imageUrl,
            article.url
        )
    }
}