package com.andres.nyuzuk.data.entity.remote

class PublisherRemoteTest {
    companion object {
        private const val SOME_ID = "fox-news"
        private const val SOME_NAME = "Fox news"
        fun create() = PublisherRemote(SOME_ID, SOME_NAME)
    }
}