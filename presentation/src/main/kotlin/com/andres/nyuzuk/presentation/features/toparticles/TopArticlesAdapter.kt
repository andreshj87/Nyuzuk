package com.andres.nyuzuk.presentation.features.toparticles

import android.view.View
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.ArticlesAdapter
import com.andres.nyuzuk.presentation.base.ImageLoader
import kotlinx.android.synthetic.main.item_article_card.view.*

class TopArticlesAdapter(
  articlesUi: List<ArticleUi>,
  articleClickListener: ArticleClickListener,
  private val imageLoader: ImageLoader
): ArticlesAdapter(articlesUi, articleClickListener) {
  override fun getLayoutResource(): Int {
    return R.layout.item_article_card
  }

  override fun getViewHolder(itemView: View): ArticleViewHolder {
    return TopArticleViewHolder(itemView)
  }

  inner class TopArticleViewHolder(itemView: View): ArticleViewHolder(itemView) {
    private val articleCardView = itemView.view_article_card
    private val articleImage = itemView.image_article
    private val articleTitleText = itemView.text_article_title

    override fun bindItem(articleUi: ArticleUi) {
      articleUi.imageUrl?.let {
        imageLoader.load(it, articleImage)
      }
      articleTitleText.text = articleUi.title
    }

    override fun getClickableView(): View {
      return articleCardView
    }
  }
}