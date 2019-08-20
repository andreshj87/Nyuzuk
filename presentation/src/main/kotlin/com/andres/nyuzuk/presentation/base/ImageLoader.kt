package com.andres.nyuzuk.presentation.base

import android.widget.ImageView

interface ImageLoader {
    fun load(imageUrl: String, imageView: ImageView)
}