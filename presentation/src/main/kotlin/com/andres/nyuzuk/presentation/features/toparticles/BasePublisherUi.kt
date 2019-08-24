package com.andres.nyuzuk.presentation.features.toparticles

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasePublisherUi(
    val id: String?,
    val name: String
) : Parcelable