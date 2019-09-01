package com.andres.nyuzuk.data.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.dummy.PublisherDummyFactory
import com.andres.nyuzuk.data.entity.local.PublisherEntity
import com.andres.nyuzuk.data.entity.remote.PublisherRemote
import com.andres.nyuzuk.domain.entity.Publisher
import org.junit.Test

class PublisherMapperTest: UnitTest() {
    private val SOME_PUBLISHER_ENTITY = PublisherDummyFactory.createPublisherEntity()
    private val SOME_PUBLISHER_REMOTE = PublisherDummyFactory.createPublisherRemote()
    private val SOME_PUBLISHER = PublisherDummyFactory.createPublisher()

    private lateinit var publisherMapper: PublisherMapper

    override fun onSetup() {
        publisherMapper = PublisherMapper()
    }

    @Test
    fun `should get null publisher when mapping null publisherRemote`() {
        val publisherRemote: PublisherRemote? = null

        val publisher = publisherMapper.map(publisherRemote)

        assertThat(publisher).isNull()
    }

    @Test
    fun `should map publisherRemote into publisher`() {
        val publisher = publisherMapper.map(SOME_PUBLISHER_REMOTE)

        assertThat(publisher).isEqualTo(SOME_PUBLISHER)
    }

    @Test
    fun `should get null publisher when mapping null publisherEntity`() {
        val publisherEntity: PublisherEntity? = null

        val publisher = publisherMapper.map(publisherEntity)

        assertThat(publisher).isNull()
    }

    @Test
    fun `should map publisherEntity into publisher`() {
        val publisher = publisherMapper.map(SOME_PUBLISHER_ENTITY)

        assertThat(publisher).isEqualTo(SOME_PUBLISHER)
    }

    @Test
    fun `should get null publisherEntity when mapping null publisher`() {
        val publisher: Publisher? = null

        val publisherEntity = publisherMapper.map(publisher)

        assertThat(publisherEntity).isNull()
    }

    @Test
    fun `should map publisher into publisherEntity`() {
        val publisherEntity = publisherMapper.map(SOME_PUBLISHER)

        assertThat(publisherEntity).isEqualTo(SOME_PUBLISHER_ENTITY)
    }
}