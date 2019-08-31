package com.andres.nyuzuk.domain.usecase

import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.andres.nyuzuk.domain.UnitTest
import com.andres.nyuzuk.domain.entity.ArticleTest
import com.andres.nyuzuk.domain.executor.PostExecutionThread
import com.andres.nyuzuk.domain.executor.ThreadExecutor
import com.andres.nyuzuk.domain.repository.ArticleRepository
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class SearchArticlesTest : UnitTest() {
    private val someQuery = "nvidia gpu"
    private val someInvalidate = false
    private val someFetchMore = true
    private val someParams = SearchArticles.Params(someQuery, someInvalidate, someFetchMore)
    private val someArticle = ArticleTest.create()
    private val someArticles = listOf(someArticle)

    @Mock lateinit var articleRepositoryMock: ArticleRepository
    @Mock lateinit var threadExecutorMock: ThreadExecutor
    @Mock lateinit var postExecutionThreadMock: PostExecutionThread

    private lateinit var searchArticles: SearchArticles

    override fun onSetup() {
        searchArticles = SearchArticles(articleRepositoryMock, threadExecutorMock, postExecutionThreadMock)
    }

    @Test
    fun `should search articles in repository`() {
        val someResult = Either.Right(someArticles)
        runBlocking {
            `when`(articleRepositoryMock.searchArticles(someQuery, someInvalidate, someFetchMore)).thenReturn(flowOf(someResult))
        }

        val articlesSearchFlow = runBlocking {
            searchArticles.execute(someParams)
        }

        verifyBlocking(articleRepositoryMock) {
            searchArticles(eq(someQuery), eq(someInvalidate), eq(someFetchMore))
        }
        assertThat(articlesSearchFlow).isNotNull()
        runBlocking {
            articlesSearchFlow.collect {
                assertThat(it).isEqualTo(someResult)
            }
        }
    }
}