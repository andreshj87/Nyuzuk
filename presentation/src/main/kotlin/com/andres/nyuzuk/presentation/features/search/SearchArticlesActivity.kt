package com.andres.nyuzuk.presentation.features.search

import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.features.toparticles.ArticleClickListener
import com.andres.nyuzuk.presentation.tools.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_search_articles.recyclerview_search_articles
import org.koin.android.ext.android.inject

class SearchArticlesActivity :
    BaseActivity<SearchArticlesViewState, SearchArticlesViewModel>(SearchArticlesViewModel::class) {
    private val imageLoader: ImageLoader by inject()
    private var searchArticlesAdapter: SearchArticlesAdapter? = null

    override fun getLayoutResource() = R.layout.activity_search_articles

    override fun render(viewState: SearchArticlesViewState) {
        searchArticlesAdapter?.apply {
            if (viewState.foundArticlesUi.isEmpty()) {
                clear()
            } else {
                update(viewState.foundArticlesUi)
            }
        }
    }

    override fun setupUi() {
        setTitle(R.string.title_search_articles)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        recyclerview_search_articles?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            searchArticlesAdapter = SearchArticlesAdapter(mutableListOf(), viewModel as ArticleClickListener, imageLoader)
            adapter = searchArticlesAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTextTyped("health") // TODO
    }
}