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

class GetTopArticlesTest: UnitTest() {
    private val someInvalidate = false
    private val someFetchMore = true
    private val someParams = GetTopArticles.Params(someInvalidate, someFetchMore)
    private val someArticle = ArticleTest.create()
    private val someArticles = listOf(someArticle)

    @Mock lateinit var articleRepositoryMock: ArticleRepository
    @Mock lateinit var threadExecutorMock: ThreadExecutor
    @Mock lateinit var postExecutionThreadMock: PostExecutionThread

    private lateinit var getTopArticles: GetTopArticles

    override fun onSetup() {
        getTopArticles = GetTopArticles(articleRepositoryMock, threadExecutorMock, postExecutionThreadMock)
    }

    @Test
    internal fun `should get top articles from repository`() {
        val someResult = Either.Right(someArticles)
        runBlocking {
            `when`(articleRepositoryMock.getTopArticles(someInvalidate, someFetchMore)).thenReturn(flowOf(someResult))
        }

        val topArticlesFlow = runBlocking {
            getTopArticles.execute(someParams)
        }

        verifyBlocking(articleRepositoryMock) { getTopArticles(eq(someInvalidate), eq(someFetchMore)) }
        assertThat(topArticlesFlow).isNotNull()
        runBlocking {
            topArticlesFlow.collect {
                assertThat(it).isEqualTo(someResult)
            }
        }
    }
}