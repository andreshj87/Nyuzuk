package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.tools.EndlessScrollListener
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
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
        setupList()
        viewModel.onInit()
        viewModel.articles.observe(this, Observer {
            updateList(it)
        })
    }

    private fun setupList() {
        recyclerview_top_articles?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            addOnScrollListener(object: EndlessScrollListener(linearLayoutManager) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    viewModel.onLoadMore()
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) { }
            })
            topArticlesAdapter = TopArticlesAdapter(mutableListOf(), viewModel as ArticleClickListener, imageLoader)
            adapter = topArticlesAdapter
        }
    }

    private fun updateList(articlesUi: List<ArticleUi>) {
        topArticlesAdapter?.apply {
            updateList(articlesUi)
        }
    }
}
