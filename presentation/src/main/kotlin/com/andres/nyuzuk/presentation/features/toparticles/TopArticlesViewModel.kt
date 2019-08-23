package com.andres.nyuzuk.presentation.features.toparticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.GetTopArticles

class TopArticlesViewModel(
    private val getTopArticles: GetTopArticles,
    private val articleUiMapper: ArticleUiMapper
) : ViewModel(), ArticleClickListener {
    val articles = MutableLiveData<List<ArticleUi>>()

    fun onInit() {
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onLoadMore() {
        getTopArticles(viewModelScope, GetTopArticles.Params()) { it.fold(::processFailure, ::processSuccess) }
    }

    fun onRefresh() {
        getTopArticles(viewModelScope, GetTopArticles.Params(true)) { it.fold(::processFailure, ::processSuccess) }
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        // TODO navigate to article detail
    }

    private fun processSuccess(articles: List<Article>) {
        this.articles.postValue(articleUiMapper.map(articles))
    }

    private fun processFailure(failure: Failure) {
        // TODO
    }
}