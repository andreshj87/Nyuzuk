package com.andres.nyuzuk.presentation.features.toparticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andres.nyuzuk.domain.usecase.GetTopHeadlines

class TopArticlesViewModel(
    private val getTopHeadlines: GetTopHeadlines,
    private val articleUiMapper: ArticleUiMapper
): ViewModel(), ArticleClickListener {
    val articles = MutableLiveData<List<ArticleUi>>()

    fun onInit() {
        getTopHeadlines(viewModelScope) {
            it.either({
                // TODO render error
            }, { articles ->
                println(articles.toString())
                this.articles.postValue(articleUiMapper.map(articles))
            })
        }
    }

    override fun onArticleClick(articleUi: ArticleUi) {
        // TODO navigate to article detail
    }
}