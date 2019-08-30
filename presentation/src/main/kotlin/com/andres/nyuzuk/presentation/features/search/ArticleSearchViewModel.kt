package com.andres.nyuzuk.presentation.features.search

import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.SearchArticles
import com.andres.nyuzuk.presentation.base.ArticleClickListener
import com.andres.nyuzuk.presentation.base.ArticleUi
import com.andres.nyuzuk.presentation.base.ArticleUiMapper
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.base.ErrorUiMapper

class ArticleSearchViewModel(
    private val searchArticles: SearchArticles,
    private val articleUiMapper: ArticleUiMapper,
    private val errorUiMapper: ErrorUiMapper
) : BaseViewModel<ArticleSearchViewState>(), ArticleClickListener {
    private var query: String? = null
    private var articlesSearch = mutableListOf<ArticleUi>()

    override fun initViewState() {
        viewState.value = ArticleSearchViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(isInitial = true)
    }

    fun onLoadMore() {
        query?.run {
            searchArticles(
                viewModelScope,
                SearchArticles.Params(query = this, fetchMore = true)
            ) { it.fold(::processFailure, ::processMoreSuccess) }
        }
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        viewState.value = getViewState().copy(articleUiToNavigate = articleUi)
        viewState.value = getViewState().copy(articleUiToNavigate = null)
    }

    fun onSearchClick(query: String) {
        query
            .apply { trim() }
            .takeIf { it.isNotEmpty() && it.length > 3 }
            ?.also {
                this.query = it
                search(it)
            }
    }

    fun onSearchClose() {
        query = null
        viewState.value = getViewState().copy(
            isInitial = true,
            isLoading = false,
            isEmpty = false,
            isError = false,
            errorUi = null,
            foundArticlesUi = emptyList()
        )
    }

    fun onErrorDialogDismiss() {
        viewState.value = getViewState().copy(isError = false, errorUi = null)
    }

    private fun search(query: String) {
        viewState.value = getViewState().copy(isLoading = true, isInitial = false, isEmpty = false, isError = false)
        searchArticles(viewModelScope, SearchArticles.Params(query, invalidate = true)) {
            it.fold(
                ::processFailure,
                ::processSuccess
            )
        }
    }

    private fun processSuccess(articles: List<Article>) {
        viewState.value = getViewState().copy(invalidateList = true, isLoading = false, isError = false)
        val articlesUi = articleUiMapper.map(articles)
        this.articlesSearch.clear()
        this.articlesSearch.addAll(articlesUi)
        if (articlesUi.isEmpty()) {
            viewState.value = getViewState().copy(isEmpty = true, foundArticlesUi = emptyList(), invalidateList = false)
        } else {
            viewState.value = getViewState().copy(foundArticlesUi = articlesUi, isEmpty = false, invalidateList = false)
        }
    }

    private fun processMoreSuccess(articles: List<Article>) {
        val articlesUi = articleUiMapper.map(articles)
        this.articlesSearch.addAll(articlesUi)
        viewState.value =
            getViewState().copy(foundArticlesUi = this.articlesSearch, isEmpty = this.articlesSearch.isEmpty())
    }

    private fun processFailure(failure: Failure) {
        if (failure !is Failure.EmptyResult) {
            val errorUi = errorUiMapper.map(failure)
            viewState.value = getViewState().copy(isError = true, errorUi = errorUi, isLoading = false)
        }
    }
}