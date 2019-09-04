package com.andres.nyuzuk.presentation.mapper

import com.andres.nyuzuk.domain.entity.Publisher
import com.andres.nyuzuk.presentation.entity.PublisherUi

class PublisherUiMapper {
    fun map(publisher: Publisher?): PublisherUi? {
        return if (publisher == null) {
            null
        } else {
            PublisherUi(publisher.id, publisher.name)
        }
    }
}