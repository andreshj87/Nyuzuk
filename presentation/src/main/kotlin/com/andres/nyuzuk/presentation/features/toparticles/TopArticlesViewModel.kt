package com.andres.nyuzuk.presentation.features.toparticles

import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.GetTopArticles
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.ErrorUiMapper

class TopArticlesViewModel(
    private val getTopArticles: GetTopArticles,
    private val articleUiMapper: ArticleUiMapper,
    private val errorUiMapper: ErrorUiMapper
) : BaseViewModel<TopArticlesViewState>(), ArticleClickListener {
    override fun initViewState() {
        viewState.value = TopArticlesViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(isLoading = true)
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onLoadMore() {
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onRefresh() {
        viewState.value = getViewState().copy(isLoading = true)
        getTopArticles(viewModelScope, GetTopArticles.Params(true)) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onErrorDialogDismiss() {
        viewState.value = getViewState().copy(isError = false, errorUi = null)
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        viewState.value = getViewState().copy(articleUiToNavigate = articleUi)
        viewState.value = getViewState().copy(articleUiToNavigate = null)
    }

    private fun processSuccess(articles: List<Article>) {
        viewState.value = getViewState().copy(isLoading = false, isError = false, errorUi = null)
        val articlesUi = articleUiMapper.map(articles)
        if (articlesUi.isEmpty()) {
            viewState.value = getViewState().copy(isEmpty = true)
        } else {
            viewState.value =
                getViewState().copy(topArticlesUi = articlesUi, isEmpty = false)
        }
    }

    private fun processFailure(failure: Failure) {
        if (failure !is Failure.EmptyResult) {
            val errorUi = errorUiMapper.map(failure)
            viewState.value = getViewState().copy(isError = true, errorUi = errorUi, isLoading = false)
        }
    }
}