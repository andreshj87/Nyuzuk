package com.andres.nyuzuk.data.repository

import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.datasource.local.ArticleLocalDataSource
import com.andres.nyuzuk.data.datasource.remote.ArticleRemoteDataSource
import com.andres.nyuzuk.data.dummy.ArticleDummyFactory
import com.andres.nyuzuk.data.mapper.ArticleMapper
import com.andres.nyuzuk.domain.Failure
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`

class ArticleDataRepositoryTest: UnitTest() {
    private val SOME_INVALIDATE = true
    private val SOME_FETCH_MORE = true
    private val SOME_RESPONSE = Either.Left(Failure.NotFoundError)
    private val SOME_ARTICLE_ENTITY = ArticleDummyFactory.createArticleEntity()
    private val SOME_ARTICLES_ENTITY = listOf(SOME_ARTICLE_ENTITY)
    private val SOME_ARTICLE = ArticleDummyFactory.createArticle()
    private val SOME_ARTICLES = listOf(SOME_ARTICLE)
    private val SOME_ARTICLE_REMOTE = ArticleDummyFactory.createArticleRemote()
    private val SOME_ARTICLES_REMOTE = listOf(SOME_ARTICLE_REMOTE)
    private val SOME_ERROR = Failure.UnknownError
    private val SOME_QUERY = "technology"

    private lateinit var articleDataRepository: ArticleDataRepository

    @Mock private lateinit var articleRemoteDataSourceMock: ArticleRemoteDataSource
    @Mock private lateinit var articleLocalDataSourceMock: ArticleLocalDataSource
    @Mock private lateinit var articleMapperMock: ArticleMapper

    override fun onSetup() {
        articleDataRepository =
            ArticleDataRepository(articleRemoteDataSourceMock, articleLocalDataSourceMock, articleMapperMock)
    }

    @Test
    fun `should invalidate top articles in local data source when getting top articles and invalidating`() {
        val invalidating = true
        mockSomeTopArticlesResponse(invalidating)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(invalidating, SOME_FETCH_MORE)
        }

        runBlocking {
            topArticlesFlow.collect { }
            verify(articleLocalDataSourceMock).invalidateTopArticles()
        }
    }

    @Test
    fun `should not invalidate top articles in local data source when getting top artices and not invalidating`() {
        val notInvalidating = false
        mockSomeTopArticlesResponse(notInvalidating)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(notInvalidating, SOME_FETCH_MORE)
        }

        runBlocking {
            topArticlesFlow.collect { }
            verify(articleLocalDataSourceMock, never()).invalidateTopArticles()
        }
    }

    @Test
    fun `should get top articles from local as first response when not invalidating and not fetching more`() {
        val notInvalidating = false
        val notFetchingMore = false
        mockSomeTopArticlesResponse(notInvalidating)
        `when`(articleLocalDataSourceMock.getTopArticles()).thenReturn(SOME_ARTICLES_ENTITY)
        `when`(articleMapperMock.mapFromLocal(SOME_ARTICLES_ENTITY)).thenReturn(SOME_ARTICLES)
        val responseExpected = Either.Right(SOME_ARTICLES)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(notInvalidating, notFetchingMore)
        }

        runBlocking {
            val firstResponse = topArticlesFlow.first()
            verify(articleLocalDataSourceMock).getTopArticles()
            assertThat(firstResponse).isEqualTo(responseExpected)
            firstResponse
        }
    }

    @Test
    fun `should not get top articles from local when invalidating and not fetching more`() {
        val invalidating = true
        val notFetchingMore = false
        mockSomeTopArticlesResponse(invalidating)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(invalidating, notFetchingMore)
        }

        runBlocking {
            topArticlesFlow.collect { }
            verify(articleLocalDataSourceMock, never()).getTopArticles()
        }
    }

    @Test
    fun `should not get top articles from local when not invalidating and fetching more`() {
        val notInvalidating = false
        val fetchingMore = true
        mockSomeTopArticlesResponse(notInvalidating)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(notInvalidating, fetchingMore)
        }

        runBlocking {
            topArticlesFlow.collect { }
            verify(articleLocalDataSourceMock, never()).getTopArticles()
        }
    }

    @Test
    fun `should not get top articles from local when invalidating and fetching more`() {
        val invalidating = true
        val fetchingMore = true
        mockSomeTopArticlesResponse(invalidating)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(invalidating, fetchingMore)
        }

        runBlocking {
            topArticlesFlow.collect { }
            verify(articleLocalDataSourceMock, never()).getTopArticles()
        }
    }

    @Test
    fun `should get NetworkConnection error when error getting top articles from remote`() {
        runBlocking {
            `when`(articleRemoteDataSourceMock.getTopArticles(SOME_INVALIDATE)).thenReturn(Either.Left(SOME_ERROR))
        }
        mockSomeTopArticlesResponse(SOME_INVALIDATE)
        val responseExpected = Either.Left(Failure.NetworkConnection)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(SOME_INVALIDATE, SOME_FETCH_MORE)
        }

        runBlocking {
            topArticlesFlow.collect {
                verify(articleRemoteDataSourceMock).getTopArticles(eq(SOME_INVALIDATE))
                assertThat(it).isEqualTo(responseExpected)
            }
        }
    }

    @Test
    fun `should save top articles in local when getting top articles from remote`() {
        runBlocking {
            `when`(articleRemoteDataSourceMock.getTopArticles(SOME_INVALIDATE)).thenReturn(Either.Right(SOME_ARTICLES_REMOTE))
        }
        `when`(articleMapperMock.mapFromRemote(SOME_ARTICLES_REMOTE)).thenReturn(SOME_ARTICLES)
        `when`(articleMapperMock.mapToLocal(SOME_ARTICLES, isTop = true)).thenReturn(SOME_ARTICLES_ENTITY)
        val articlesEntityExpected = SOME_ARTICLES_ENTITY

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(SOME_INVALIDATE, SOME_FETCH_MORE)
        }

        runBlocking {
            topArticlesFlow.collect { }
            verify(articleLocalDataSourceMock).save(eq(articlesEntityExpected))
        }
    }

    @Test
    fun `should get top articles from remote`() {
        runBlocking {
            `when`(articleRemoteDataSourceMock.getTopArticles(SOME_INVALIDATE)).thenReturn(Either.Right(SOME_ARTICLES_REMOTE))
        }
        `when`(articleMapperMock.mapFromRemote(SOME_ARTICLES_REMOTE)).thenReturn(SOME_ARTICLES)
        `when`(articleMapperMock.mapToLocal(SOME_ARTICLES, isTop = true)).thenReturn(SOME_ARTICLES_ENTITY)
        val responseExpected = Either.Right(SOME_ARTICLES)

        val topArticlesFlow = runBlocking {
            articleDataRepository.getTopArticles(SOME_INVALIDATE, SOME_FETCH_MORE)
        }

        runBlocking {
            topArticlesFlow.collect {
                verify(articleRemoteDataSourceMock).getTopArticles(eq(SOME_INVALIDATE))
                assertThat(it).isEqualTo(responseExpected)
            }
        }
    }

    @Test
    fun `should get articles from local as first response when searching articles not fetching more`() {
        `when`(articleLocalDataSourceMock.getArticlesSearch(SOME_QUERY)).thenReturn(SOME_ARTICLES_ENTITY)
        `when`(articleMapperMock.mapFromLocal(SOME_ARTICLES_ENTITY)).thenReturn(SOME_ARTICLES)
        val responseExpected = Either.Right(SOME_ARTICLES)

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, SOME_INVALIDATE, false)
        }

        runBlocking {
            val firstResponse = articlesSearchFlow.first()
            assertThat(firstResponse).isEqualTo(responseExpected)
            firstResponse
        }
    }

    @Test
    fun `should not get articles from local when searching articles fetching more`() {
        mockSomeArticlesSearchResponse(SOME_QUERY, SOME_INVALIDATE)

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, SOME_INVALIDATE, true)
        }

        runBlocking {
            articlesSearchFlow.collect { }
            verify(articleLocalDataSourceMock, never()).getArticlesSearch(anyString())
        }
    }

    @Test
    fun `should invalidate articles search in local when searching articles invalidating`() {
        mockSomeArticlesSearchResponse(SOME_QUERY, SOME_INVALIDATE)

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, true, SOME_FETCH_MORE)
        }

        runBlocking {
            articlesSearchFlow.collect { }
            verify(articleLocalDataSourceMock).invalidateArticlesSearch(eq(SOME_QUERY))
        }
    }

    @Test
    fun `should not invalidate articles search in local when searching articles not invalidating`() {
        val notInvalidating = false
        mockSomeArticlesSearchResponse(SOME_QUERY, notInvalidating)

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, notInvalidating, SOME_FETCH_MORE)
        }

        runBlocking {
            articlesSearchFlow.collect { }
            verify(articleLocalDataSourceMock, never()).invalidateArticlesSearch(anyString())
        }
    }

    @Test
    fun `should get NetworkConnection error when searching articles and error from remote`() {
        runBlocking {
            `when`(articleRemoteDataSourceMock.searchArticles(SOME_QUERY, SOME_INVALIDATE)).thenReturn(Either.Left(SOME_ERROR))
        }
        val responseExpected =  Either.Left(Failure.NetworkConnection)

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, SOME_INVALIDATE, SOME_FETCH_MORE)
        }

        runBlocking {
            articlesSearchFlow.collect {
                verify(articleRemoteDataSourceMock).searchArticles(eq(SOME_QUERY), eq(SOME_INVALIDATE))
                assertThat(it).isEqualTo(responseExpected)
            }
        }
    }

    @Test
    fun `should save articles search in local when searching articles in remote`() {
        runBlocking {
            `when`(articleRemoteDataSourceMock.searchArticles(SOME_QUERY, SOME_INVALIDATE)).thenReturn(Either.Right(SOME_ARTICLES_REMOTE))
        }
        `when`(articleMapperMock.mapFromRemote(SOME_ARTICLES_REMOTE)).thenReturn(SOME_ARTICLES)
        `when`(articleMapperMock.mapToLocal(SOME_ARTICLES, searchQuery = SOME_QUERY)).thenReturn(SOME_ARTICLES_ENTITY)
        val articlesSearchExpected = SOME_ARTICLES_ENTITY

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, SOME_INVALIDATE, SOME_FETCH_MORE)
        }

        runBlocking {
            articlesSearchFlow.collect { }
            verify(articleLocalDataSourceMock).save(eq(articlesSearchExpected))
        }
    }

    @Test
    fun `should get articles search from remote`() {
        runBlocking {
            `when`(articleRemoteDataSourceMock.searchArticles(SOME_QUERY, SOME_INVALIDATE)).thenReturn(Either.Right(SOME_ARTICLES_REMOTE))
        }
        `when`(articleMapperMock.mapFromRemote(SOME_ARTICLES_REMOTE)).thenReturn(SOME_ARTICLES)
        `when`(articleMapperMock.mapToLocal(SOME_ARTICLES)).thenReturn(SOME_ARTICLES_ENTITY)
        val articlesSearchExpected = Either.Right(SOME_ARTICLES)

        val articlesSearchFlow = runBlocking {
            articleDataRepository.searchArticles(SOME_QUERY, SOME_INVALIDATE, SOME_FETCH_MORE)
        }

        runBlocking {
            articlesSearchFlow.collect {
                verify(articleRemoteDataSourceMock).searchArticles(eq(SOME_QUERY), eq(SOME_INVALIDATE))
                assertThat(it).isEqualTo(articlesSearchExpected)
            }
        }
    }

    private fun mockSomeTopArticlesResponse(isInvalidating: Boolean) {
        runBlocking {
            `when`(articleRemoteDataSourceMock.getTopArticles(isInvalidating)).thenReturn(SOME_RESPONSE)
        }
    }

    private fun mockSomeArticlesSearchResponse(query: String, isInvalidating: Boolean) {
        runBlocking {
            `when`(articleRemoteDataSourceMock.searchArticles(query, isInvalidating)).thenReturn(SOME_RESPONSE)
        }
    }
}