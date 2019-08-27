package com.andres.nyuzuk.data.mapper

import com.andres.nyuzuk.data.entity.local.ArticleEntity
import com.andres.nyuzuk.data.entity.remote.ArticleRemote
import com.andres.nyuzuk.domain.entity.Article

class ArticleMapper(
    private val publisherMapper: PublisherMapper
) {
    fun mapFromRemote(articlesRemote: List<ArticleRemote>) = articlesRemote.map { map(it) }

    private fun map(articleRemote: ArticleRemote) = Article(
        articleRemote.title,
        articleRemote.description,
        articleRemote.content,
        articleRemote.author,
        publisherMapper.map(articleRemote.publisher),
        articleRemote.urlToImage,
        articleRemote.url
    )

    fun mapFromLocal(articlesEntity: List<ArticleEntity>) = articlesEntity.map { map(it) }

    private fun map(articleEntity: ArticleEntity) = Article(
        articleEntity.title,
        articleEntity.description,
        articleEntity.content,
        articleEntity.author,
        publisherMapper.map(articleEntity.publisher),
        articleEntity.imageUrl,
        articleEntity.url
    )

    fun mapToLocal(articles: List<Article>, isTop: Boolean = false, searchQuery: String = "") =
        articles.map { map(it, isTop, searchQuery) }

    fun map(article: Article, isTop: Boolean = false, searchQuery: String = "") = ArticleEntity(
        article.url,
        article.title,
        article.description,
        article.content,
        article.author,
        publisherMapper.map(article.publisher),
        article.imageUrl,
        isTop,
        searchQuery
    )
}