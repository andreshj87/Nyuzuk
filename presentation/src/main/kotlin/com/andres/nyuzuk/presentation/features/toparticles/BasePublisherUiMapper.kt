package com.andres.nyuzuk.presentation.features.toparticles

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