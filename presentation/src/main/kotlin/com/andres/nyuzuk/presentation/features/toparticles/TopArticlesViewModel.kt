package com.andres.nyuzuk.presentation.features.toparticles

import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.GetTopArticles
import com.andres.nyuzuk.presentation.base.ArticleClickListener
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.mapper.ArticleUiMapper
import com.andres.nyuzuk.presentation.mapper.ErrorUiMapper
import com.andres.nyuzuk.presentation.tools.Navigator

class TopArticlesViewModel(
    private val getTopArticles: GetTopArticles,
    private val articleUiMapper: ArticleUiMapper,
    private val errorUiMapper: ErrorUiMapper,
    private val navigator: Navigator
) : BaseViewModel<TopArticlesViewState>(), ArticleClickListener {
    private val topArticlesUi = mutableListOf<ArticleUi>()

    override fun initViewState() {
        viewState.value = TopArticlesViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(isLoading = true)
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onLoadMore() {
        getTopArticles(viewModelScope, GetTopArticles.Params(fetchMore = true)) { it.fold(::processFailure, ::processMoreSuccess) }
    }

    fun onRefresh() {
        viewState.value = getViewState().copy(isLoading = true)
        getTopArticles(viewModelScope, GetTopArticles.Params(invalidate = true)) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onErrorDialogDismiss() {
        viewState.value = getViewState().copy(isError = false, errorUi = null)
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        navigator.navigateToDetail(articleUi)
    }

    private fun processSuccess(articles: List<Article>) {
        this.topArticlesUi.clear()
        viewState.value = getViewState().copy(invalidateList = true)
        processArticles(articles)
    }

    private fun processMoreSuccess(articles: List<Article>) {
        processArticles(articles)
    }

    private fun processArticles(articles: List<Article>) {
        this.topArticlesUi.addAll(articleUiMapper.map(articles))
        viewState.value = getViewState().copy(
            isLoading = false,
            topArticlesUi = this.topArticlesUi,
            invalidateList = false,
            isEmpty = this.topArticlesUi.isEmpty(),
            isError = false,
            errorUi = null
        )
    }

    private fun processFailure(failure: Failure) {
        viewState.value = getViewState().copy(isLoading = false)
        if (failure !is Failure.EmptyResult) {
            val errorUi = errorUiMapper.map(failure)
            viewState.value = getViewState().copy(isError = true, errorUi = errorUi)
        }
    }
}