package com.andres.nyuzuk.presentation.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PublisherUi(
    val id: String?,
    val name: String
) : Parcelable