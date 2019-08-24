package com.andres.nyuzuk.presentation.base

import com.andres.nyuzuk.domain.entity.BasePublisher

class BasePublisherUiMapper {
    fun map(basePublisher: BasePublisher?): BasePublisherUi? {
        return if (basePublisher != null) {
            BasePublisherUi(basePublisher.id, basePublisher.name)
        } else {
            null
        }
    }
}