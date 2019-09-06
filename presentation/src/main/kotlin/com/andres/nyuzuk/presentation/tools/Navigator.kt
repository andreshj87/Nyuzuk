package com.andres.nyuzuk.presentation.tools

import android.content.Context
import android.content.Intent
import com.andres.nyuzuk.presentation.entity.ArticleUi
import com.andres.nyuzuk.presentation.features.detail.ArticleDetailActivity
import com.andres.nyuzuk.presentation.features.search.ArticleSearchActivity

class Navigator(
    private val context: Context
) {
    fun navigateToSearch() {
        navigate { ArticleSearchActivity.makeIntent(context) }
    }

    fun navigateToDetail(articleUi: ArticleUi) {
        navigate { ArticleDetailActivity.makeIntent(context, articleUi) }
    }

    private fun navigate(block: () -> Intent) {
        context.startActivity(block())
    }
}