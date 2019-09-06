package com.andres.nyuzuk.presentation.features.toparticles

import androidx.lifecycle.viewModelScope
import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.GetTopArticles
import com.andres.nyuzuk.presentation.ViewModelUnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import com.andres.nyuzuk.presentation.dummyfactory.ErrorDummyFactory
import com.andres.nyuzuk.presentation.mapper.ArticleUiMapper
import com.andres.nyuzuk.presentation.mapper.ErrorUiMapper
import com.andres.nyuzuk.presentation.tools.Navigator
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class TopArticlesViewModelTest: ViewModelUnitTest<TopArticlesViewState, TopArticlesViewModel>() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()
    private val SOME_ARTICLES_UI = listOf(SOME_ARTICLE_UI)
    private val SOME_ARTICLE = ArticleDummyFactory.createArticle()
    private val SOME_ARTICLES = listOf(SOME_ARTICLE)
    private val SOME_ERROR_UI = ErrorDummyFactory.createErrorUi()

    @Mock private lateinit var getTopArticlesMock: GetTopArticles
    @Mock private lateinit var articleUiMapperMock: ArticleUiMapper
    @Mock private lateinit var errorUiMapperMock: ErrorUiMapper
    @Mock private lateinit var navigatorMock: Navigator
    private val onResultCaptor = argumentCaptor<(Either<Failure, List<Article>>) -> Unit>()

    override fun onSetup() {
        viewModel = TopArticlesViewModel(getTopArticlesMock, articleUiMapperMock, errorUiMapperMock, navigatorMock)
    }

    @Test
    fun `should set default viewState when init view state`() {
        val viewStateExpected = TopArticlesViewState()

        viewModel.initViewState()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should set viewState with true isLoading when view ready`() {
        val viewStateExpected = TopArticlesViewState().copy(isLoading = true)

        viewModel.onViewReady()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should invoke getTopArticles use case when view ready`() {
        val viewModelScopeExpected = viewModel.viewModelScope
        val paramsExpected = GetTopArticles.Params()

        viewModel.onViewReady()

        verify(getTopArticlesMock).invoke(eq(viewModelScopeExpected), eq(paramsExpected), any())
    }

    @Test
    fun `should invoke getTopArticles with true fetchMore param when load more`() {
        val viewModelScopeExpected = viewModel.viewModelScope
        val paramsExpected = GetTopArticles.Params(fetchMore = true)

        viewModel.onLoadMore()

        verify(getTopArticlesMock).invoke(eq(viewModelScopeExpected), eq(paramsExpected), any())
    }

    @Test
    fun `should set viewState with true isLoading when refresh`() {
        val viewStateExpected = TopArticlesViewState().copy(isLoading = true)

        viewModel.onRefresh()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should invoke getTopArticles with true invalidate when refresh`() {
        val viewModelScopeExpected = viewModel.viewModelScope
        val paramsExpected = GetTopArticles.Params(invalidate  = true)

        viewModel.onRefresh()

        verify(getTopArticlesMock).invoke(eq(viewModelScopeExpected), eq(paramsExpected), any())
    }

    @Test
    fun `should set viewState with false isError and null errorUi when error dialog dismiss`() {
        val viewStateExpected = TopArticlesViewState().copy(isError = false, errorUi = null)

        viewModel.onErrorDialogDismiss()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should navigate to article detail when article click`() {
        viewModel.onArticleClick(SOME_ARTICLE_UI)

        verify(navigatorMock).navigateToDetail(eq(SOME_ARTICLE_UI))
    }

    @Test
    fun `should render the proper viewState when processSuccess`() {
        `when`(articleUiMapperMock.map(SOME_ARTICLES)).thenReturn(SOME_ARTICLES_UI)
        viewModel.onViewReady()
        verify(getTopArticlesMock).invoke(any(), any(), onResultCaptor.capture())
        val viewStateExpected = TopArticlesViewState().copy(
            isLoading = false,
            topArticlesUi = SOME_ARTICLES_UI,
            invalidateList = false,
            isEmpty = SOME_ARTICLES_UI.isEmpty(),
            isError = false,
            errorUi = null
        )

        onResultCaptor.firstValue(Either.Right(SOME_ARTICLES))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should render error in viewState when processFailure and failure is not EmptyResult`() {
        viewModel.onViewReady()
        verify(getTopArticlesMock).invoke(eq(viewModel.viewModelScope), eq(GetTopArticles.Params()), onResultCaptor.capture())
        val notEmptyResultFailure = Failure.NetworkConnection
        `when`(errorUiMapperMock.map(notEmptyResultFailure)).thenReturn(SOME_ERROR_UI)
        val viewStateExpected = TopArticlesViewState().copy(
            isError = true,
            errorUi = SOME_ERROR_UI,
            isLoading = false
        )

        onResultCaptor.firstValue(Either.Left(notEmptyResultFailure))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should not render error in viewState when processFailure and failure is EmptyResult`() {
        viewModel.onViewReady()
        verify(getTopArticlesMock).invoke(eq(viewModel.viewModelScope), eq(GetTopArticles.Params()), onResultCaptor.capture())
        val emptyResultFailure = Failure.EmptyResult
        val viewStateExpected = TopArticlesViewState()

        onResultCaptor.firstValue(Either.Left(emptyResultFailure))

        verifyZeroInteractions(errorUiMapperMock)
        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should render the proper viewState when processMoreSuccess`() {
        `when`(articleUiMapperMock.map(SOME_ARTICLES)).thenReturn(SOME_ARTICLES_UI)
        viewModel.onLoadMore()
        verify(getTopArticlesMock).invoke(any(), any(), onResultCaptor.capture())
        val viewStateExpected = TopArticlesViewState().copy(
            isLoading = false,
            topArticlesUi = SOME_ARTICLES_UI,
            invalidateList = false,
            isEmpty = SOME_ARTICLES_UI.isEmpty(),
            isError = false,
            errorUi = null
        )

        onResultCaptor.firstValue(Either.Right(SOME_ARTICLES))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should render the full list of articlesUi in viewState when processMoreSuccess after processMore`() {
        `when`(articleUiMapperMock.map(SOME_ARTICLES)).thenReturn(SOME_ARTICLES_UI)
        viewModel.onViewReady()
        verify(getTopArticlesMock).invoke(any(), eq(GetTopArticles.Params()), onResultCaptor.capture())
        onResultCaptor.firstValue(Either.Right(SOME_ARTICLES))
        viewModel.onLoadMore()
        verify(getTopArticlesMock).invoke(any(), eq(GetTopArticles.Params(fetchMore = true)), onResultCaptor.capture())
        val viewStateExpected = TopArticlesViewState().copy(
            isLoading = false,
            topArticlesUi = SOME_ARTICLES_UI.plus(SOME_ARTICLES_UI),
            invalidateList = false,
            isEmpty = false,
            isError = false,
            errorUi = null
        )

        onResultCaptor.secondValue(Either.Right(SOME_ARTICLES))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }
}