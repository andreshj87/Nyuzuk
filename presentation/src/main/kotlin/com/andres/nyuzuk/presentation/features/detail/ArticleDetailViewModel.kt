package com.andres.nyuzuk.presentation.features.detail

import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.tools.webbrowser.WebBrowser

class ArticleDetailViewModel(
    private val webBrowser: WebBrowser
): BaseViewModel<ArticleDetailViewState>() {
    override fun initViewState() {
        viewState.value = ArticleDetailViewState()
    }

    override fun onViewReady() { }

    fun onArticleLoaded(articleUi: ArticleUi?) {
        viewState.value = getViewStateValue().copy(articleUi = articleUi)
    }

    fun onSeeMoreClick() {
        getViewStateValue().articleUi?.run {
            webBrowser.launch(url)
        }
    }
}