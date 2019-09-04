package com.andres.nyuzuk.presentation.dummyfactory

import com.andres.nyuzuk.domain.entity.Publisher
import com.andres.nyuzuk.presentation.entity.PublisherUi

class PublisherDummyFactory {
    companion object {
        private const val SOME_ID = "fox-news"
        private const val SOME_NAME = "Fox news"

        fun createPublisher() = Publisher(
            SOME_ID,
            SOME_NAME
        )

        fun createPublisherUi() = PublisherUi(
            SOME_ID,
            SOME_NAME
        )
    }
}