package com.andres.nyuzuk.presentation.features.toparticles

import com.andres.nyuzuk.domain.entity.BasePublisher

data class ArticleUi(
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val publisher: BasePublisher?,
    val imageUrl: String?,
    val url: String
)