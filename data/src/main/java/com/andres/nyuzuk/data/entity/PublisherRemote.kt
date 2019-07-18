package com.andres.nyuzuk.data.entity

import com.squareup.moshi.Json

data class PublisherRemote(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String
)