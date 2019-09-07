package com.andres.nyuzuk.presentation.features.detail

import com.andres.nyuzuk.presentation.base.BaseViewState
import com.andres.nyuzuk.presentation.entity.ArticleUi

data class ArticleDetailViewState(
    val articleUi: ArticleUi? = null
) : BaseViewState