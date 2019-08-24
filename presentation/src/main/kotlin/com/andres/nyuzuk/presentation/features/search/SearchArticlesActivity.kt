package com.andres.nyuzuk.presentation.features.search

import android.view.Menu
import androidx.appcompat.widget.SearchView
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
            clear()
            if (viewState.foundArticlesUi.isNotEmpty()) {
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
            itemAnimator = null
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            searchArticlesAdapter = SearchArticlesAdapter(mutableListOf(), viewModel as ArticleClickListener, imageLoader)
            adapter = searchArticlesAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        menu?.let {
            val searchItem = menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String) = false

                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.onSearchClick(query)
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }
}