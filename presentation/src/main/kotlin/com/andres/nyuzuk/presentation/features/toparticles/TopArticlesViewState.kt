package com.andres.nyuzuk.presentation.features.toparticles

import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.base.ErrorUi

data class TopArticlesViewState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val errorUi: ErrorUi? = null,
    val topArticlesUi: List<ArticleUi> = emptyList(),
    val articleUiToNavigate: ArticleUi? = null
): BaseViewState