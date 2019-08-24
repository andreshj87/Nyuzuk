package com.andres.nyuzuk.presentation.features.search

import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.SearchArticles
import com.andres.nyuzuk.presentation.base.BaseViewModel
import com.andres.nyuzuk.presentation.features.toparticles.ArticleClickListener
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUi
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUiMapper

class SearchArticlesViewModel(
    private val searchArticles: SearchArticles,
    private val articleUiMapper: ArticleUiMapper
): BaseViewModel<SearchArticlesViewState>(), ArticleClickListener {
    override fun initViewState() {
        viewState.value = SearchArticlesViewState()
    }

    override fun onViewReady() {
        viewState.value = getViewState().copy(isInitial = true)
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        // TODO navigate to article detail
    }

    fun onSearchClick(query: String) {
        query
            .apply { trim() }
            .also { attemptSearch(it) }
    }

    private fun attemptSearch(query: String) {
        if (query.isNotEmpty() && query.length > 3) {
            search(query)
        }
    }

    private fun search(typedText: String) {
        viewState.value = getViewState().copy(isLoading = true)
        searchArticles(viewModelScope, SearchArticles.Params(typedText)) { it.fold(::processFailure, ::processSuccess) }
    }

    private fun processSuccess(articles: List<Article>) {
        viewState.value = getViewState().copy(isInitial = false, isLoading = false, isError = false)
        val articlesUi = articleUiMapper.map(articles)
        if (articlesUi.isEmpty()) {
            viewState.value = getViewState().copy(isEmpty = true)
        } else {
            viewState.value = getViewState().copy(foundArticlesUi = articlesUi, isEmpty = false)
        }
    }

    private fun processFailure(failure: Failure) {
        viewState.value = getViewState().copy(isError = true, isLoading = false)
    }
}