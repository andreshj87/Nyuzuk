package com.andres.nyuzuk.presentation.tools.webbrowser

import android.content.Context

interface WebBrowser {
    fun launch(context: Context, url: String)
}