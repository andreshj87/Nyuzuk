package com.andres.nyuzuk.presentation.features.detail

import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.entity.ArticleUi

class ArticleDetailViewModel: BaseViewModel<ArticleDetailViewState>() {
    override fun initViewState() {
        viewState.value = ArticleDetailViewState()
    }

    override fun onViewReady() { }

    fun onArticleLoaded(articleUi: ArticleUi?) {
        viewState.value = getViewState().copy(articleUi = articleUi)
    }

    fun onSeeMoreClick() {
        viewState.value = getViewState().copy(navigateToDetail = true) // TODO How to test 2 viewState changes in the same method? Is it even a good practice?
        viewState.value = getViewState().copy(navigateToDetail = false)
    }
}