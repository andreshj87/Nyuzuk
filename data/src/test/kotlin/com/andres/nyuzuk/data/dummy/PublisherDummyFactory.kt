package com.andres.nyuzuk.data.dummy

import com.andres.nyuzuk.data.entity.local.PublisherEntity
import com.andres.nyuzuk.data.entity.remote.PublisherRemote
import com.andres.nyuzuk.domain.entity.Publisher

class PublisherDummyFactory {
    companion object {
        private const val SOME_ID = "fox-news"
        private const val SOME_NAME = "Fox news"

        fun createPublisherRemote() = PublisherRemote(
            SOME_ID,
            SOME_NAME
        )

        fun createPublisherEntity() = PublisherEntity(
            id = SOME_ID,
            name = SOME_NAME
        )

        fun createPublisher() = Publisher(
            SOME_ID,
            SOME_NAME
        )
    }
}