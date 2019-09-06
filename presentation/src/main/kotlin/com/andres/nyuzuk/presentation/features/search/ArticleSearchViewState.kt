package com.andres.nyuzuk.presentation.features.search

import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.entity.ErrorUi

data class ArticleSearchViewState(
    val isInitial: Boolean = false,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val errorUi: ErrorUi? = null,
    val invalidateList: Boolean = false,
    val foundArticlesUi: List<ArticleUi> = emptyList()
) : BaseViewState