package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.tools.EndlessScrollListener
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_top_articles.recyclerview_top_articles
import kotlinx.android.synthetic.main.activity_top_articles.view_swipe_to_refresh
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopArticlesActivity : AppCompatActivity() {
    private val viewModel: TopArticlesViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private var topArticlesAdapter: TopArticlesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_articles)
        setupUi()
        viewModel.onViewReady()
        viewModel.viewState.observe(this, Observer {
            render(it)
        })
    }

    private fun setupUi() {
        recyclerview_top_articles?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            addOnScrollListener(object : EndlessScrollListener(linearLayoutManager) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    viewModel.onLoadMore()
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            })
            topArticlesAdapter = TopArticlesAdapter(mutableListOf(), viewModel as ArticleClickListener, imageLoader)
            adapter = topArticlesAdapter
        }
        view_swipe_to_refresh?.apply {
            setOnRefreshListener { viewModel.onRefresh() }
        }
    }

    private fun render(viewState: TopArticlesViewState?) {
        viewState?.let {
            view_swipe_to_refresh.isRefreshing = viewState.isLoading
            // TODO isEmpty
            // TODO isError
            topArticlesAdapter?.apply {
                if (viewState.topArticlesUi.isEmpty()) {
                    clear()
                } else {
                    update(viewState.topArticlesUi)
                }
            }
        }
    }
}
