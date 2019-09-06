package com.andres.nyuzuk.presentation.features.search

import androidx.lifecycle.viewModelScope
import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import com.andres.nyuzuk.domain.Failure
import com.andres.nyuzuk.domain.entity.Article
import com.andres.nyuzuk.domain.usecase.SearchArticles
import com.andres.nyuzuk.presentation.ViewModelUnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import com.andres.nyuzuk.presentation.dummyfactory.ErrorDummyFactory
import com.andres.nyuzuk.presentation.mapper.ArticleUiMapper
import com.andres.nyuzuk.presentation.mapper.ErrorUiMapper
import com.andres.nyuzuk.presentation.tools.Navigator
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class ArticleSearchViewModelTest : ViewModelUnitTest<ArticleSearchViewState, ArticleSearchViewModel>() {
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()
    private val SOME_QUERY = "technology"
    private val SOME_SHORTER_QUERY = "te"
    private val SOME_ARTICLE = ArticleDummyFactory.createArticle()
    private val SOME_ARTICLES = listOf(SOME_ARTICLE)
    private val SOME_ARTICLES_UI = listOf(SOME_ARTICLE_UI)
    private val SOME_FAILURE = Failure.MappingError
    private val SOME_ERROR_UI = ErrorDummyFactory.createErrorUi()

    @Mock private lateinit var searchArticlesMock: SearchArticles
    @Mock private lateinit var articleUiMapperMock: ArticleUiMapper
    @Mock private lateinit var errorUiMapperMock: ErrorUiMapper
    @Mock private lateinit var navigatorMock: Navigator
    private val onResultCaptor = argumentCaptor<(Either<Failure, List<Article>>) -> Unit>()

    override fun onSetup() {
        viewModel = ArticleSearchViewModel(searchArticlesMock, articleUiMapperMock, errorUiMapperMock, navigatorMock)
    }

    @Test
    fun `should set default viewState when init view state`() {
        val viewStateExpected = ArticleSearchViewState()

        viewModel.initViewState()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should set viewState with true isInitial when view ready`() {
        val viewStateExpected = ArticleSearchViewState().copy(isInitial = true)

        viewModel.onViewReady()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should not search articles when load more and query was not previously set`() {
        viewModel.onLoadMore()

        verifyZeroInteractions(searchArticlesMock)
    }

    @Test
    fun `should navigate to article detail when article click`() {
        viewModel.onArticleClick(SOME_ARTICLE_UI)

        verify(navigatorMock).navigateToDetail(eq(SOME_ARTICLE_UI))
    }

    @Test
    fun `should search articles when search click with query longer than 3 characters`() {
        viewModel.onSearchClick(SOME_QUERY)

        verify(searchArticlesMock).invoke(any(), any(), any())
    }

    @Test
    fun `should set viewStat accordingly when search click with query longer than 3 characters`() {
        val viewStateExpected =
            ArticleSearchViewState().copy(isLoading = true, isInitial = false, isEmpty = false, isError = false)

        viewModel.onSearchClick(SOME_QUERY)

        assertThat(viewStateExpected).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should not search articles when search click with empty query`() {
        viewModel.onSearchClick("")

        verifyZeroInteractions(searchArticlesMock)
    }

    @Test
    fun `should not search articles when search click with query shorter than 3 characters`() {
        viewModel.onSearchClick(SOME_SHORTER_QUERY)

        verifyZeroInteractions(searchArticlesMock)
    }

    @Test
    fun `should reset viewState accordingly when seach close`() {
        val viewStateExpected = ArticleSearchViewState().copy(
            isInitial = true,
            isLoading = false,
            isEmpty = false,
            isError = false,
            errorUi = null,
            foundArticlesUi = emptyList()
        )

        viewModel.onSearchClose()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should set viewState with no errors when error dialog dismiss`() {
        val viewStateExpected = ArticleSearchViewState().copy(isError = false, errorUi = null)

        viewModel.onErrorDialogDismiss()

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should search again when load more and query was previously set`() {
        viewModel.onSearchClick(SOME_QUERY)

        viewModel.onLoadMore()

        verify(searchArticlesMock, times(2)).invoke(any(), any(), any())
    }

    @Test
    fun `should not search again when load more and query is not currently set although it previously was`() {
        viewModel.onSearchClick(SOME_QUERY)
        viewModel.onSearchClose()

        viewModel.onLoadMore()

        verify(searchArticlesMock, only()).invoke(any(), any(), any())
    }

    @Test
    fun `should search articles with true invalidate when search click`() {
        val paramsExpected = SearchArticles.Params(query = SOME_QUERY, invalidate = true)

        viewModel.onSearchClick(SOME_QUERY)

        verify(searchArticlesMock).invoke(any(), eq(paramsExpected), any())
    }

    @Test
    fun `should search articles with true fetchMore when load more`() {
        viewModel.onSearchClick(SOME_QUERY)
        val paramsExpected = SearchArticles.Params(query = SOME_QUERY, fetchMore = true)

        viewModel.onLoadMore()

        verify(searchArticlesMock).invoke(any(), eq(paramsExpected), any())
    }

    @Test
    fun `should update viewState accordingly when success searching articles`() {
        `when`(articleUiMapperMock.map(SOME_ARTICLES)).thenReturn(SOME_ARTICLES_UI)
        viewModel.onSearchClick(SOME_QUERY)
        verify(searchArticlesMock).invoke(eq(viewModel.viewModelScope), any(), onResultCaptor.capture())
        val viewStateExpected = ArticleSearchViewState().copy(
            isLoading = false,
            isError = false,
            isEmpty = false,
            invalidateList = false,
            foundArticlesUi = SOME_ARTICLES_UI
        )

        onResultCaptor.firstValue(Either.Right(SOME_ARTICLES))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should update viewState accordingly when success search articles with empty results`() {
        `when`(articleUiMapperMock.map(emptyList())).thenReturn(emptyList())
        viewModel.onSearchClick(SOME_QUERY)
        verify(searchArticlesMock).invoke(eq(viewModel.viewModelScope), any(), onResultCaptor.capture())
        val viewStateExpected = ArticleSearchViewState().copy(
            isLoading = false,
            isError = false,
            isEmpty = true,
            invalidateList = false,
            foundArticlesUi = emptyList()
        )

        onResultCaptor.firstValue(Either.Right(emptyList()))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should update viewState accordingly when success searching more`() {
        `when`(articleUiMapperMock.map(SOME_ARTICLES)).thenReturn(SOME_ARTICLES_UI)
        viewModel.onSearchClick(SOME_QUERY)
        val paramsSearch = SearchArticles.Params(query = SOME_QUERY, invalidate = true)
        verify(searchArticlesMock).invoke(eq(viewModel.viewModelScope), eq(paramsSearch), onResultCaptor.capture())
        onResultCaptor.firstValue(Either.Right(SOME_ARTICLES))
        viewModel.onLoadMore()
        val paramsLoadMore = SearchArticles.Params(query = SOME_QUERY, fetchMore = true)
        verify(searchArticlesMock).invoke(eq(viewModel.viewModelScope), eq(paramsLoadMore), onResultCaptor.capture())
        val articlesUiExpected = SOME_ARTICLES_UI.plus(SOME_ARTICLES_UI)
        val viewStateExpected = ArticleSearchViewState().copy(
            isLoading = false,
            isError = false,
            isEmpty = articlesUiExpected.isEmpty(),
            invalidateList = false,
            foundArticlesUi = articlesUiExpected
        )

        onResultCaptor.secondValue(Either.Right(SOME_ARTICLES))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should update viewState accordingly when failure searching articles`() {
        `when`(errorUiMapperMock.map(SOME_FAILURE)).thenReturn(SOME_ERROR_UI)
        viewModel.onSearchClick(SOME_QUERY)
        verify(searchArticlesMock).invoke(any(), any(), onResultCaptor.capture())
        val viewStateExpected = ArticleSearchViewState().copy(isError = true, errorUi = SOME_ERROR_UI, isLoading = false)

        onResultCaptor.firstValue(Either.Left(SOME_FAILURE))

        assertThat(getViewStateValue()).isEqualTo(viewStateExpected)
    }

    @Test
    fun `should not show error when empty failure searching articles`() {
        viewModel.onSearchClick(SOME_QUERY)
        verify(searchArticlesMock).invoke(any(), any(), onResultCaptor.capture())

        onResultCaptor.firstValue(Either.Left(Failure.EmptyResult))

        verifyZeroInteractions(errorUiMapperMock)
        assertThat(getViewStateValue()!!.isError).isFalse()
        assertThat(getViewStateValue()!!.errorUi).isNull()
    }
}