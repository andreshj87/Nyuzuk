package com.andres.nyuzuk.presentation.mapper

import android.content.Context
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.R
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.presentation.UnitTest
import com.andres.nyuzuk.presentation.entity.ErrorUi
import com.nhaarman.mockitokotlin2.eq
import org.junit.Test
import org.mockito.AdditionalMatchers.not
import org.mockito.Mock
import org.mockito.Mockito.`when`

class ErrorUiMapperTest: UnitTest() {
    private val SOME_ERROR_TITLE = "Error title"
    private val SOME_ERROR_MESSAGE = "Some error message"
    private val SOME_ERROR = Failure.ApiError

    private lateinit var errorUiMapper: ErrorUiMapper

    @Mock private lateinit var contextMock: Context

    override fun onSetup() {
        errorUiMapper = ErrorUiMapper(contextMock)
    }

    @Test
    fun `should get errorUi with the right title`() {
        val titleExpected = SOME_ERROR_TITLE
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(not(eq(R.string.error_title)))).thenReturn(SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(SOME_ERROR)

        assertThat(errorUi.title).isEqualTo(titleExpected)
    }

    @Test
    fun `should map network connection error`() {
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(R.string.error_network_connection)).thenReturn(SOME_ERROR_MESSAGE)
        val errorUiExpected = ErrorUi(SOME_ERROR_TITLE, SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(Failure.NetworkConnection)

        assertThat(errorUi).isEqualTo(errorUiExpected)
    }

    @Test
    fun `should map api error`() {
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(R.string.error_api)).thenReturn(SOME_ERROR_MESSAGE)
        val errorUiExpected = ErrorUi(SOME_ERROR_TITLE, SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(Failure.ApiError)

        assertThat(errorUi).isEqualTo(errorUiExpected)
    }

    @Test
    fun `should map empty error`() {
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(R.string.error_empty)).thenReturn(SOME_ERROR_MESSAGE)
        val errorUiExpected = ErrorUi(SOME_ERROR_TITLE, SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(Failure.EmptyResult)

        assertThat(errorUi).isEqualTo(errorUiExpected)
    }

    @Test
    fun `should map mapping error`() {
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(R.string.error_mapping)).thenReturn(SOME_ERROR_MESSAGE)
        val errorUiExpected = ErrorUi(SOME_ERROR_TITLE, SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(Failure.MappingError)

        assertThat(errorUi).isEqualTo(errorUiExpected)
    }

    @Test
    fun `should map not found error`() {
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(R.string.error_not_found)).thenReturn(SOME_ERROR_MESSAGE)
        val errorUiExpected = ErrorUi(SOME_ERROR_TITLE, SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(Failure.NotFoundError)

        assertThat(errorUi).isEqualTo(errorUiExpected)
    }

    @Test
    fun `should map unknown error`() {
        `when`(contextMock.getString(R.string.error_title)).thenReturn(SOME_ERROR_TITLE)
        `when`(contextMock.getString(R.string.error_unknown)).thenReturn(SOME_ERROR_MESSAGE)
        val errorUiExpected = ErrorUi(SOME_ERROR_TITLE, SOME_ERROR_MESSAGE)

        val errorUi = errorUiMapper.map(Failure.UnknownError)

        assertThat(errorUi).isEqualTo(errorUiExpected)
    }
}