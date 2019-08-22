package com.andres.nyuzuk.data.mapper

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
}