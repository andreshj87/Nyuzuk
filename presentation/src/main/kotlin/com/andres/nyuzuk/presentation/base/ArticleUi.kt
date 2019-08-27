package com.andres.nyuzuk.presentation.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleUi(
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val publisher: PublisherUi?,
    val imageUrl: String?,
    val url: String
): Parcelable