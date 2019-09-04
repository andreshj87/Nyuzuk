package com.andres.nyuzuk.presentation.base

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.UnitTest
import com.andres.nyuzuk.presentation.dummyfactory.PublisherDummyFactory
import com.andres.nyuzuk.presentation.mapper.PublisherUiMapper
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.assertThat
import org.junit.Test

class PublisherUiMapperTest: UnitTest() {
    private val SOME_PUBLISHER = PublisherDummyFactory.createPublisher()
    private val SOME_PUBLISHER_UI = PublisherDummyFactory.createPublisherUi()

    private lateinit var publisherUiMapper: PublisherUiMapper

    override fun onSetup() {
        publisherUiMapper = PublisherUiMapper()
    }

    @Test
    fun `should get null when mapping null publisher`() {
        val publisherUi = publisherUiMapper.map(null)

        assertThat(publisherUi, nullValue())
    }

    @Test
    fun `should get publisherUi when mapping valid publisher`() {
        val publisherUiExpected = SOME_PUBLISHER_UI

        val publisherUi = publisherUiMapper.map(SOME_PUBLISHER)

        assertThat(publisherUi).isEqualTo(publisherUiExpected)
    }
}