package com.andres.nyuzuk.presentation.features.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUi
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_article_detail.button_article_see_more
import kotlinx.android.synthetic.main.activity_article_detail.image_article
import kotlinx.android.synthetic.main.activity_article_detail.text_article_author
import kotlinx.android.synthetic.main.activity_article_detail.text_article_body
import kotlinx.android.synthetic.main.activity_article_detail.text_article_title
import org.koin.android.ext.android.inject

class ArticleDetailActivity :
    BaseActivity<ArticleDetailViewState, ArticleDetailViewModel>(ArticleDetailViewModel::class) {
    private val imageLoader: ImageLoader by inject()

    companion object {
        private val EXTRA_ARTICLE_UI = ArticleDetailActivity::class.java.simpleName.plus(".EXTRA_ARTICLE_UI")

        fun makeIntent(context: Context, articleUi: ArticleUi) =
            Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE_UI, articleUi)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val articleUi = intent?.extras?.getParcelable<ArticleUi>(EXTRA_ARTICLE_UI)
        viewModel.onArticleLoaded(articleUi)
    }

    override fun getLayoutResource() = R.layout.activity_article_detail

    override fun render(viewState: ArticleDetailViewState) {
        viewState.articleUi?.let {
            if (viewState.navigateToDetail) {
                // TODO open webview
            }
            val articleUi = viewState.articleUi
            articleUi.imageUrl?.let { imageLoader.load(articleUi.imageUrl, image_article) }
            text_article_title.text = articleUi.title
            articleUi.author?.let { text_article_author.text = articleUi.author }
            if (articleUi.content != null) {
                text_article_body.text = articleUi.content
            } else if (articleUi.description != null) {
                text_article_body.text = articleUi.description
            }
        }
    }

    override fun setupUi() {
        setTitle(R.string.article_detail_title)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        button_article_see_more.setOnClickListener { viewModel.onSeeMoreClick() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}