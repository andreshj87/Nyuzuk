package com.andres.nyuzuk.presentation.base

import com.andres.nyuzuk.presentation.entity.ArticleUi

interface ArticleClickListener {
    fun onArticleClick(articleUi: ArticleUi)
}