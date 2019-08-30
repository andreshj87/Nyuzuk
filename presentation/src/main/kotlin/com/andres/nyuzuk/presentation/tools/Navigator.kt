package com.andres.nyuzuk.presentation.tools

import android.content.Context
import android.content.Intent
import com.andres.nyuzuk.presentation.features.detail.ArticleDetailActivity
import com.andres.nyuzuk.presentation.features.search.ArticleSearchActivity
import com.andres.nyuzuk.presentation.entity.ArticleUi

class Navigator {
    fun navigateToSearch(context: Context) {
        navigate(context) { ArticleSearchActivity.makeIntent(context) }
    }

    fun navigateToDetail(context: Context, articleUi: ArticleUi) {
        navigate(context) { ArticleDetailActivity.makeIntent(context, articleUi) }
    }

    private fun navigate(context: Context, block: () -> Intent) {
        context.startActivity(block())
    }
}