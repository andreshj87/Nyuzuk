package com.andres.nyuzuk.presentation.features.toparticles

import com.andres.nyuzuk.domain.entity.Article

class ArticleUiMapper() {
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
        article.publisher,
        article.imageUrl,
        article.url
    )
  }
}