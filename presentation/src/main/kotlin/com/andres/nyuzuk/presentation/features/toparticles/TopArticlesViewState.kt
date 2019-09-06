package com.andres.nyuzuk.presentation.features.toparticles

import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.entity.ErrorUi

data class TopArticlesViewState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val errorUi: ErrorUi? = null,
    val topArticlesUi: List<ArticleUi> = emptyList(),
    val invalidateList: Boolean = false
): BaseViewState