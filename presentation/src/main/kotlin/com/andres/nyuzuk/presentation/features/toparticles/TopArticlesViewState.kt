package com.andres.nyuzuk.presentation.features.toparticles

import com.andres.nyuzuk.presentation.base.BaseViewState

data class TopArticlesViewState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val topArticlesUi: List<ArticleUi> = emptyList()
): BaseViewState