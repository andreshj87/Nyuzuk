package com.andres.nyuzuk.presentation.tools

import android.content.Context
import android.content.Intent
import com.andres.nyuzuk.presentation.features.search.SearchArticlesActivity

class Navigator {
    fun navigateToSearch(context: Context) {
        val intent = Intent(context, SearchArticlesActivity::class.java)
        context.startActivity(intent)
    }
}