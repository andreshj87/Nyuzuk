package com.andres.nyuzuk.presentation.features.toparticles

data class TopArticlesViewState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val topArticlesUi: List<ArticleUi> = emptyList()
)