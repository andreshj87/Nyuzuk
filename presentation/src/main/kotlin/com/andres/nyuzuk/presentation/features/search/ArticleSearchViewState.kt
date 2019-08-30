package com.andres.nyuzuk.presentation.features.search

import com.andres.nyuzuk.presentation.base.ArticleUi
import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.base.ErrorUi

data class ArticleSearchViewState(
    val isInitial: Boolean = false,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val errorUi: ErrorUi? = null,
    val invalidateList: Boolean = false,
    val foundArticlesUi: List<ArticleUi> = emptyList(),
    val articleUiToNavigate: ArticleUi? = null
) : BaseViewState