package com.andres.nyuzuk.data.datasource.local.database

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.dummy.PublisherDummyFactory
import com.andres.nyuzuk.data.entity.local.PublisherEntity
import com.andres.nyuzuk.data.tools.jsonserializer.JsonSerializer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.util.Date

class ConvertersTest: UnitTest() {
    private val SOME_PUBLISHER_ENTITY = PublisherDummyFactory.createPublisherEntity()
    private val SOME_PUBLISHER_ENTITY_IN_JSON = "{type:'publisherEntity', format:'json'}"
    private val SOME_DATE = Date()
    private val SOME_DATE_IN_LONG = SOME_DATE.time

    private lateinit var converters: Converters

    @Mock private lateinit var jsonSerializerMock: JsonSerializer

    override fun onSetup() {
        converters = Converters()
        converters.jsonSerializer = jsonSerializerMock
    }

    @Test
    fun `should get empty string when converting null publisherEntity to json`() {
        val publisherEntityInJson = converters.publisherEntityToJson(null)

        verifyZeroInteractions(jsonSerializerMock)
        assertThat(publisherEntityInJson).isEqualTo("")
    }

    @Test
    fun `should convert publisherEntity to json`() {
        `when`(jsonSerializerMock.toJson(SOME_PUBLISHER_ENTITY)).thenReturn(SOME_PUBLISHER_ENTITY_IN_JSON)

        val publisherEntityInJson = converters.publisherEntityToJson(SOME_PUBLISHER_ENTITY)

        assertThat(publisherEntityInJson).isNotNull()
        assertThat(publisherEntityInJson).isEqualTo(SOME_PUBLISHER_ENTITY_IN_JSON)
    }

    @Test
    fun `should get null publisherEntity when converting empty json`() {
        val publisherEntityFromJson = converters.publisherEntityFromJson("")

        verifyZeroInteractions(jsonSerializerMock)
        assertThat(publisherEntityFromJson).isNull()
    }

    @Test
    fun `should get publisherEntity from json`() {
        `when`(jsonSerializerMock.fromJson(SOME_PUBLISHER_ENTITY_IN_JSON, PublisherEntity::class.java)).thenReturn(SOME_PUBLISHER_ENTITY)

        val publisherEntityFromJson = converters.publisherEntityFromJson(SOME_PUBLISHER_ENTITY_IN_JSON)

        verify(jsonSerializerMock).fromJson(eq(SOME_PUBLISHER_ENTITY_IN_JSON), eq(PublisherEntity::class.java))
        assertThat(publisherEntityFromJson).isEqualTo(SOME_PUBLISHER_ENTITY)
    }

    @Test
    fun `should get null date when converting null long date`() {
        val date = converters.toDate(null)

        assertThat(date).isNull()
    }

    @Test
    fun `should get date from long date`() {
        val date = converters.toDate(SOME_DATE_IN_LONG)

        assertThat(date).isEqualTo(SOME_DATE)
    }

    @Test
    fun `should get null long date from null date`() {
        val dateInLong = converters.fromDate(null)

        assertThat(dateInLong).isNull()
    }

    @Test
    fun `should get long date from date`() {
        val dateInLong = converters.fromDate(SOME_DATE)

        assertThat(dateInLong).isEqualTo(SOME_DATE_IN_LONG)
    }
}