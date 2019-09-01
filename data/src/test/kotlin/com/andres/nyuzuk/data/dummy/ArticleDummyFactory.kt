package com.andres.nyuzuk.data.dummy

import com.andres.nyuzuk.data.entity.local.ArticleEntity
import com.andres.nyuzuk.data.entity.remote.ArticleRemote
import com.andres.nyuzuk.data.extension.toDate
import com.andres.nyuzuk.domain.entity.Article

class ArticleDummyFactory {
    companion object {
        private const val SOME_TITLE = "Coastal Georgia colleges brace for Hurricane Dorian"
        private const val SOME_DESCRIPTION =
            "Colleges along Georgia’s coast are preparing their campuses for wet, stormy weather ahead of Hurricane Dorian"
        private const val SOME_CONTENT =
            "The Category 5 hurricane, which has maximum sustained winds of 180 mph, is predicted to make landfall in the Carolinas by midweek and move along..."
        private const val SOME_AUTHOR = "Asia Simone Burns"
        private const val SOME_IMAGE_URL =
            "https://www.ajc.com/rf/image_large/Pub/p10/AJC/2019/09/01/Images/EDWPPfcXsAAbe_g.jpg"
        private const val SOME_URL =
            "https://www.ajc.com/news/breaking-news/coastal-georgia-colleges-brace-for-hurricane-dorian/Kp7b6dQXog1DENKdun0rXN/"
        private val SOME_PUBLISHED_AT = "2019-09-01T18:18:26Z"
        private val SOME_PUBLISHED_AT_DATE = SOME_PUBLISHED_AT.toDate()
        private val SOME_PUBLISHER = PublisherDummyFactory.createPublisher()
        private val SOME_PUBLISHER_REMOTE = PublisherDummyFactory.createPublisherRemote()
        private val SOME_PUBLISHER_ENTITY = PublisherDummyFactory.createPublisherEntity()
        private const val SOME_IS_TOP = true
        private const val SOME_SEARCH_QUERY = "technology"

        fun createArticleRemote() = ArticleRemote(
            SOME_PUBLISHER_REMOTE,
            SOME_AUTHOR,
            SOME_TITLE,
            SOME_DESCRIPTION,
            SOME_URL,
            SOME_IMAGE_URL,
            SOME_CONTENT,
            SOME_PUBLISHED_AT
        )

        fun createArticleEntity() = ArticleEntity(
            SOME_URL,
            SOME_TITLE,
            SOME_DESCRIPTION,
            SOME_CONTENT,
            SOME_AUTHOR,
            SOME_PUBLISHER_ENTITY,
            SOME_IMAGE_URL,
            SOME_IS_TOP,
            SOME_SEARCH_QUERY,
            SOME_PUBLISHED_AT_DATE
        )

        fun createArticle() = Article(
            SOME_TITLE,
            SOME_DESCRIPTION,
            SOME_CONTENT,
            SOME_AUTHOR,
            SOME_PUBLISHER,
            SOME_IMAGE_URL,
            SOME_URL,
            SOME_PUBLISHED_AT_DATE
        )
    }
}