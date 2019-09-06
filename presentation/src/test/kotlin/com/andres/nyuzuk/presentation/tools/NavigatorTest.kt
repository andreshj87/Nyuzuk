package com.andres.nyuzuk.presentation.tools

import android.content.Context
import android.content.Intent
import assertk.assertThat
import assertk.assertions.isNotNull
import com.andres.nyuzuk.presentation.UnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mock

class NavigatorTest: UnitTest() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()

    private lateinit var navigator: Navigator

    @Mock private lateinit var contextMock: Context
    private val intentCaptor = argumentCaptor<Intent>()

    override fun onSetup() {
        navigator = Navigator(contextMock)
    }

    @Test
    fun `should navigate to article search activity`() {
        navigator.navigateToSearch()

        verify(contextMock).startActivity(intentCaptor.capture())
        val intentCaptured = intentCaptor.firstValue
        assertThat(intentCaptured).isNotNull()
    }

    @Ignore
    @Test
    fun `should navigate to article detail`() {
        navigator.navigateToDetail(SOME_ARTICLE_UI)

        verify(contextMock).startActivity(intentCaptor.capture())
        val intentCaptured = intentCaptor.firstValue
        assertThat(intentCaptured).isNotNull()
    }
}