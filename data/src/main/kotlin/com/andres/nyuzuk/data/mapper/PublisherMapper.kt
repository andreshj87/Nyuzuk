package com.andres.nyuzuk.data.mapper

import com.andres.nyuzuk.data.entity.remote.PublisherRemote
import com.andres.nyuzuk.domain.entity.Publisher

class PublisherMapper {
    fun map(publisherRemote: PublisherRemote): Publisher {
        return Publisher(
            publisherRemote.id,
            publisherRemote.name,
            publisherRemote.description,
            publisherRemote.url
        )
    }
}