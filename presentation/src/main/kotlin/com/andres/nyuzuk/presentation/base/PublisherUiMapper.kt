package com.andres.nyuzuk.presentation.base

import com.andres.nyuzuk.domain.entity.Publisher

class PublisherUiMapper {
    fun map(publisher: Publisher?): PublisherUi? {
        return if (publisher != null) {
            PublisherUi(publisher.id, publisher.name)
        } else {
            null
        }
    }
}