package com.andres.nyuzuk.presentation.base

import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoImageLoader: ImageLoader {
    override fun load(imageUrl: String, imageView: ImageView) =
        Picasso.get().load(imageUrl).into(imageView)
}