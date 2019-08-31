package com.andres.nyuzuk.data.tools.jsonserializer

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.entity.remote.PublisherRemote
import com.andres.nyuzuk.data.entity.remote.PublisherRemoteTest
import com.nhaarman.mockitokotlin2.mock
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class MoshiJsonSerializerTest: UnitTest() {
    @Mock private lateinit var moshiMock: Moshi

    private lateinit var moshiJsonSerializer: JsonSerializer

    override fun onSetup() {
        moshiJsonSerializer = MoshiJsonSerializer(moshiMock)
    }

    @Test
    fun `should create from json`() {
        val someValue = PublisherRemoteTest.create()
        val someValueInJson = "{id:'${someValue.id}', name:'${someValue.name}'}"
        val jsonAdapterMock = mock<JsonAdapter<PublisherRemote>>()
        `when`(jsonAdapterMock.fromJson(someValueInJson)).thenReturn(someValue)
        `when`(moshiMock.adapter(someValue::class.java)).thenReturn(jsonAdapterMock)

        val fromJson = moshiJsonSerializer.fromJson(someValueInJson, someValue::class.java)

        assertThat(fromJson).isEqualTo(someValue)
    }
}