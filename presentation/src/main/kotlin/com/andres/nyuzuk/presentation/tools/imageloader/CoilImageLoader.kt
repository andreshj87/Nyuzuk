package com.andres.nyuzuk.presentation.tools.imageloader

import android.widget.ImageView
import coil.api.load

class CoilImageLoader : ImageLoader {
    override fun load(imageUrl: String, imageView: ImageView) {
        imageView.load(imageUrl)
    }
}