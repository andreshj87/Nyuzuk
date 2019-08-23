package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.tools.EndlessScrollListener
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_top_articles.recyclerview_top_articles
import kotlinx.android.synthetic.main.activity_top_articles.view_swipe_to_refresh
import org.koin.android.ext.android.inject

class TopArticlesActivity : BaseActivity<TopArticlesViewState, TopArticlesViewModel>(TopArticlesViewModel::class) {
    private val imageLoader: ImageLoader by inject()
    private var topArticlesAdapter: TopArticlesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
    }

    override fun getLayoutResource() = R.layout.activity_top_articles

    override fun render(viewState: TopArticlesViewState) {
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
}
