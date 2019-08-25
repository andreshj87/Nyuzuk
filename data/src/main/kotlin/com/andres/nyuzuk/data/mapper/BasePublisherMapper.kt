package com.andres.nyuzuk.data.mapper

import com.andres.nyuzuk.data.entity.local.BasePublisherEntity
import com.andres.nyuzuk.data.entity.remote.BasePublisherRemote
import com.andres.nyuzuk.domain.entity.BasePublisher

class BasePublisherMapper {
    fun map(publisherRemote: BasePublisherRemote?): BasePublisher? {
        return if (publisherRemote == null) {
            null
        } else {
            BasePublisher(
                publisherRemote.id,
                publisherRemote.name
            )
        }
    }

    fun map(basePublisherEntity: BasePublisherEntity?): BasePublisher? {
        return if (basePublisherEntity == null) {
            null
        } else {
            BasePublisher(
                basePublisherEntity.id,
                basePublisherEntity.name
            )
        }
    }

    fun map(basePublisher: BasePublisher?): BasePublisherEntity? {
        return if (basePublisher == null) {
            null
        } else {
            BasePublisherEntity(
                basePublisher.id,
                basePublisher.name
            )
        }
    }
}