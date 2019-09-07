package com.andres.nyuzuk.domain.entity

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.Test

class PublisherTest {
    companion object {
        private const val SOME_ID = "fox-news"
        private const val SOME_NAME = "Fox news"
        val SOME_PUBLISHER = Publisher(SOME_ID, SOME_NAME)
    }

    private lateinit var publisher: Publisher

    @Test
    internal fun `should create`() {
        publisher = Publisher(SOME_ID, SOME_NAME)

        assertThat(publisher).isNotNull()
        publisher.run {
            assertThat(id).isEqualTo(SOME_ID)
            assertThat(name).isEqualTo(SOME_NAME)
        }
    }

    @Test
    fun `should create with null id`() {
        publisher = Publisher(null, SOME_NAME)

        assertThat(publisher).isNotNull()
        publisher.run {
            assertThat(id).isNull()
            assertThat(name).isEqualTo(SOME_NAME)
        }
    }
}