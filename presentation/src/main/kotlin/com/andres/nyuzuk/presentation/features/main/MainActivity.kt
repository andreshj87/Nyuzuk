package com.andres.nyuzuk.presentation.features.main

import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.base.BaseActivity
import com.andres.nyuzuk.presentation.extension.doTransaction
import com.andres.nyuzuk.presentation.extension.setVisibility
import com.andres.nyuzuk.presentation.features.toparticles.TopArticlesFragment
import kotlinx.android.synthetic.main.activity_main.fab_search

class MainActivity : BaseActivity<MainViewState, MainViewModel>(MainViewModel::class) {
    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun render(viewState: MainViewState) {
        fab_search.setVisibility(viewState.showSearchButton)
    }

    override fun setupUi() {
        supportFragmentManager.doTransaction { add(R.id.container_top_articles, TopArticlesFragment.newInstance()) }
        fab_search.setOnClickListener { viewModel.onSearchClick() }
    }
}