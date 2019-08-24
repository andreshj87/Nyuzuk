package com.andres.nyuzuk.presentation.features.detail

import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.features.toparticles.ArticleUi

data class ArticleDetailViewState(
    val articleUi: ArticleUi? = null,
    val navigateToDetail: Boolean = false
) : BaseViewState