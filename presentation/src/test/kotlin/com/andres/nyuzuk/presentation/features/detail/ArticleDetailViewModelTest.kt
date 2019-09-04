package com.andres.nyuzuk.presentation.features.detail

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.ViewModelUnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import org.junit.Test

class ArticleDetailViewModelTest: ViewModelUnitTest<ArticleDetailViewState, ArticleDetailViewModel>() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()

    override fun onSetup() {
        viewModel = ArticleDetailViewModel()
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
    fun `should be viewState with false navigateToDetail when see more click`() {
        val viewStateExpected = ArticleDetailViewState(navigateToDetail = false)

        viewModel.onSeeMoreClick()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }
}