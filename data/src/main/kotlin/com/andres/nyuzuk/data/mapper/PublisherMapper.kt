package com.andres.nyuzuk.data.mapper

import com.andres.nyuzuk.data.entity.local.PublisherEntity
import com.andres.nyuzuk.data.entity.remote.PublisherRemote
import com.andres.nyuzuk.domain.entity.Publisher

class PublisherMapper {
    fun map(publisherRemote: PublisherRemote?): Publisher? {
        return if (publisherRemote == null) {
            null
        } else {
            Publisher(
                publisherRemote.id,
                publisherRemote.name
            )
        }
    }

    fun map(publisherEntity: PublisherEntity?): Publisher? {
        return if (publisherEntity == null) {
            null
        } else {
            Publisher(
                publisherEntity.id,
                publisherEntity.name
            )
        }
    }

    fun map(publisher: Publisher?): PublisherEntity? {
        return if (publisher == null) {
            null
        } else {
            PublisherEntity(
                id = publisher.id,
                name = publisher.name
            )
        }
    }
}