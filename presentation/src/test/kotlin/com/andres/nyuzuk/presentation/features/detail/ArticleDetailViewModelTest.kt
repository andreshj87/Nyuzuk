package com.andres.nyuzuk.presentation.features.detail

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.ViewModelUnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import com.andres.nyuzuk.presentation.tools.webbrowser.WebBrowser
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.mockito.Mock

class ArticleDetailViewModelTest: ViewModelUnitTest<ArticleDetailViewState, ArticleDetailViewModel>() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()

    @Mock private lateinit var webBrowserMock: WebBrowser

    override fun onSetup() {
        viewModel = ArticleDetailViewModel(webBrowserMock)
    }

    @Test
    fun `should be default viewState value when init view state`() {
        val viewStateExpected = ArticleDetailViewState()

        viewModel.initViewState()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should be viewState with articleUi when article loaded`() {
        val viewStateExpected = ArticleDetailViewState(articleUi = SOME_ARTICLE_UI)

        viewModel.onArticleLoaded(SOME_ARTICLE_UI)

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should navigate to article detail url when see more click`() {
        viewModel.onArticleLoaded(SOME_ARTICLE_UI)
        val urlExpected = SOME_ARTICLE_UI.url

        viewModel.onSeeMoreClick()

        verify(webBrowserMock).launch(eq(urlExpected))
    }
}