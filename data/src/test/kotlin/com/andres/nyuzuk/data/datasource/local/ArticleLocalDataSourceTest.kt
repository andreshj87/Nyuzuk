package com.andres.nyuzuk.data.datasource.local

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.datasource.local.database.ArticleDao
import com.andres.nyuzuk.data.dummy.ArticleDummyFactory
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class ArticleLocalDataSourceTest: UnitTest() {
    private val SOME_ARTICLES_ENTITY = listOf(ArticleDummyFactory.createArticleEntity())
    private val SOME_SEARCH_QUERY = "technology"

    private lateinit var articleLocalDataSource: ArticleLocalDataSource

    @Mock private lateinit var articleDaoMock: ArticleDao

    override fun onSetup() {
        articleLocalDataSource = ArticleLocalDataSource(articleDaoMock)
    }

    @Test
    fun `should save articles in dao`() {
        articleLocalDataSource.save(SOME_ARTICLES_ENTITY)

        verify(articleDaoMock).save(eq(SOME_ARTICLES_ENTITY))
    }

    @Test
    fun `should get top articles from dao`() {
        `when`(articleDaoMock.getTopArticles()).thenReturn(SOME_ARTICLES_ENTITY)

        val topArticles = articleLocalDataSource.getTopArticles()

        verify(articleDaoMock).getTopArticles()
        assertThat(topArticles).isEqualTo(SOME_ARTICLES_ENTITY)
    }

    @Test
    fun `should invalidate top articles in dao`() {
        articleLocalDataSource.invalidateTopArticles()

        verify(articleDaoMock).invalidateTopArticles()
    }

    @Test
    fun `should get articles search from dao`() {
        `when`(articleDaoMock.getArticlesSearch(SOME_SEARCH_QUERY)).thenReturn(SOME_ARTICLES_ENTITY)

        val articlesSearch = articleLocalDataSource.getArticlesSearch(SOME_SEARCH_QUERY)

        verify(articleDaoMock).getArticlesSearch(eq(SOME_SEARCH_QUERY))
        assertThat(articlesSearch).isEqualTo(SOME_ARTICLES_ENTITY)
    }

    @Test
    fun `should invalidate articles search in dao`() {
        articleLocalDataSource.invalidateArticlesSearch(SOME_SEARCH_QUERY)

        verify(articleDaoMock).invalidateArticlesSearch(eq(SOME_SEARCH_QUERY))
    }
}