package com.andres.nyuzuk.domain.entity

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import org.junit.Test
import java.util.Date

class ArticleTest {
    companion object {
        private const val SOME_TITLE = "Hurricane Dorian heads for the US"
        private const val SOME_DESCRIPTION =
            "The storm is currently a Category 2 storm heading northwest in the Atlantic. Follow here for the latest."
        private const val SOME_CONTENT =
            "As Dorian barrels towards the island chain on Saturday, a hurricane warning has been issued in areas in northwest Bahamas, according to an alert issued by the Bahamas Department of Meteorology on Friday."
        private const val SOME_AUTHOR = "Lee Moran"
        private val SOME_PUBLISHER = PublisherTest.SOME_PUBLISHER
        private const val SOME_IMAGE_URL = ""
        private const val SOME_URL = ""
        private val SOME_PUBLISHED_AT = Date()
        fun create() = Article(
            SOME_TITLE,
            SOME_DESCRIPTION,
            SOME_CONTENT,
            SOME_AUTHOR,
            SOME_PUBLISHER,
            SOME_IMAGE_URL,
            SOME_URL,
            SOME_PUBLISHED_AT
        )
    }

    private lateinit var article: Article

    @Test
    fun `should create`() {
        article = Article(
            SOME_TITLE,
            SOME_DESCRIPTION,
            SOME_CONTENT,
            SOME_AUTHOR,
            SOME_PUBLISHER,
            SOME_IMAGE_URL,
            SOME_URL,
            SOME_PUBLISHED_AT
        )

        assertThat(article).isNotNull()
        article.run {
            assertThat(title).isEqualTo(SOME_TITLE)
            assertThat(description).isEqualTo(SOME_DESCRIPTION)
            assertThat(content).isEqualTo(SOME_CONTENT)
            assertThat(author).isEqualTo(SOME_AUTHOR)
            assertThat(publisher).isEqualTo(SOME_PUBLISHER)
            assertThat(imageUrl).isEqualTo(SOME_IMAGE_URL)
            assertThat(url).isEqualTo(SOME_URL)
            assertThat(publishedAt).isEqualTo(SOME_PUBLISHED_AT)
        }
    }
}