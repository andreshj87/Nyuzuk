package com.andres.nyuzuk.presentation.features.toparticles

import com.andres.nyuzuk.domain.entity.Article

class ArticleUiMapper(
    private val basePublisherUiMapper: BasePublisherUiMapper
) {
    fun map(articles: List<Article>): List<ArticleUi> {
        return articles.map {
            map(it)
        }
    }

    fun map(article: Article): ArticleUi {
        return ArticleUi(
            article.title,
            article.description,
            article.content,
            article.author,
            basePublisherUiMapper.map(article.publisher),
            article.imageUrl,
            article.url
        )
    }
}