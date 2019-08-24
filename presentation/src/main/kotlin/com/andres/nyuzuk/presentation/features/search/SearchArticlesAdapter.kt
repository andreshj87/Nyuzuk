package com.andres.nyuzuk.presentation.features.search

import android.view.View
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.ArticlesAdapter
import com.andres.nyuzuk.presentation.features.toparticles.ArticleClickListener
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUi
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import kotlinx.android.synthetic.main.item_article_card.view.image_article
import kotlinx.android.synthetic.main.item_article_card.view.text_article_title
import kotlinx.android.synthetic.main.item_article_small.view.view_article

class SearchArticlesAdapter(
    articlesUi: MutableList<ArticleUi>,
    articleClickListener: ArticleClickListener,
    private val imageLoader: ImageLoader
) : ArticlesAdapter(articlesUi, articleClickListener) {
    override fun getLayoutResource() = R.layout.item_article_small

    override fun getViewHolder(itemView: View) = SearchArticleViewHolder(itemView)

    inner class SearchArticleViewHolder(itemView: View): ArticleViewHolder(itemView) {
        private val articleImage = itemView.image_article
        private val articleTitleText = itemView.text_article_title

        override fun bindItem(articleUi: ArticleUi) {
            articleUi.imageUrl?.let { imageLoader.load(it, articleImage) }
            articleTitleText.text = articleUi.title
        }

        override fun getClickableView(): View {
            return itemView.view_article
        }
    }
}