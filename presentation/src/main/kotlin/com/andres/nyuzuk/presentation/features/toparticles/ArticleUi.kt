package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleUi(
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val publisher: BasePublisherUi?,
    val imageUrl: String?,
    val url: String
): Parcelable