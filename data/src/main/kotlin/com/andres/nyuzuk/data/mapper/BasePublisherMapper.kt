package com.andres.nyuzuk.data.mapper

import com.andres.nyuzuk.data.entity.BasePublisherRemote
import com.andres.nyuzuk.domain.entity.BasePublisher

class BasePublisherMapper {
    fun map(publisherRemote: BasePublisherRemote): BasePublisher {
        return BasePublisher(
            publisherRemote.id,
            publisherRemote.name
        )
    }
}