package com.andres.nyuzuk.data.mapper

import com.andres.nyuzuk.data.entity.ArticleRemote
import com.andres.nyuzuk.domain.entity.Article

class ArticleMapper(
    private val publisherMapper: PublisherMapper
) {
    fun map(articleRemotes: List<ArticleRemote>): List<Article> {
        val articles = mutableListOf<Article>()
        articleRemotes.forEach {
            articles.add(map(it))
        }
        return articles
    }

    private fun map(articleRemote: ArticleRemote): Article {
        return Article(
            articleRemote.title,
            articleRemote.description,
            articleRemote.content,
            articleRemote.author,
            publisherMapper.map(articleRemote.publisher),
            articleRemote.imageUrl,
            articleRemote.url
        )
    }
}