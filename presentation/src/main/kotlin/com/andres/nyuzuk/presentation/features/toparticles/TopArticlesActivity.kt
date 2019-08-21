package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.ImageLoader
import kotlinx.android.synthetic.main.activity_top_articles.recyclerview_top_articles
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopArticlesActivity : AppCompatActivity() {
    private val viewModel: TopArticlesViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private var topArticlesAdapter: TopArticlesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_articles)
        viewModel.onInit()
        viewModel.articles.observe(this, Observer {
            loadList(it)
        })
    }

    private fun loadList(articlesUi: List<ArticleUi>) {
        recyclerview_top_articles.apply {
            layoutManager = LinearLayoutManager(context)
            if (topArticlesAdapter == null) {
                topArticlesAdapter = TopArticlesAdapter(articlesUi, viewModel as ArticleClickListener, imageLoader)
            } else {
                topArticlesAdapter?.apply { submitList(articlesUi) }
            }
            adapter = topArticlesAdapter
        }
    }
}
