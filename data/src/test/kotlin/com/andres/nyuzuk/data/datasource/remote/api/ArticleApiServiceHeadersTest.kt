package com.andres.nyuzuk.data.datasource.remote.api

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.data.UnitTest
import com.nhaarman.mockitokotlin2.eq
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class ArticleApiServiceHeadersTest: UnitTest() {
    private val SOME_API_KEY = "qwerty12345"

    private lateinit var articleApiServiceHeaders: ArticleApiServiceHeaders

    @Mock private lateinit var chainMock: Interceptor.Chain
    @Mock private lateinit var requestMock: Request
    @Mock private lateinit var requestBuilderMock: Request.Builder
    @Mock private lateinit var otherRequestMock: Request
    @Mock private lateinit var responseMock: Response

    override fun onSetup() {
        articleApiServiceHeaders = ArticleApiServiceHeaders(SOME_API_KEY)
    }

    @Test
    fun `should get response with headers when intercept`() {
        `when`(chainMock.proceed(otherRequestMock)).thenReturn(responseMock)
        `when`(requestBuilderMock.build()).thenReturn(otherRequestMock)
        `when`(requestBuilderMock.header(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(requestBuilderMock)
        `when`(requestMock.newBuilder()).thenReturn(requestBuilderMock)
        `when`(chainMock.request()).thenReturn(requestMock)

        val response = articleApiServiceHeaders.intercept(chainMock)

        verify(requestBuilderMock).header(eq("X-Api-Key"), eq(SOME_API_KEY))
        assertThat(response).isEqualTo(responseMock)
    }
}