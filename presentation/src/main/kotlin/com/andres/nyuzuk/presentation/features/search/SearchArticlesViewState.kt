package com.andres.nyuzuk.presentation.features.search

import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.base.ErrorUi
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUi

data class SearchArticlesViewState(
    val isInitial: Boolean = false,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val errorUi: ErrorUi? = null,
    val foundArticlesUi: List<ArticleUi> = emptyList(),
    val articleUiToNavigate: ArticleUi? = null
) : BaseViewState