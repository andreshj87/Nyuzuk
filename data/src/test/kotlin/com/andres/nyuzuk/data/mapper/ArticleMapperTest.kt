package com.andres.nyuzuk.data.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.data.UnitTest
import com.andres.nyuzuk.data.dummy.ArticleDummyFactory
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class ArticleMapperTest: UnitTest() {
    private val SOME_ARTICLES_REMOTE = listOf(ArticleDummyFactory.createArticleRemote())
    private val SOME_ARTICLES_ENTITY = listOf(ArticleDummyFactory.createArticleEntity())
    private val SOME_ARTICLES = listOf(ArticleDummyFactory.createArticle())

    private lateinit var articleMapper: ArticleMapper

    @Mock private lateinit var publisherMapperMock: PublisherMapper

    override fun onSetup() {
        articleMapper = ArticleMapper(publisherMapperMock)
    }

    @Test
    fun `should map from remote`() {
        `when`(publisherMapperMock.map(SOME_ARTICLES_REMOTE[0].publisher)).thenReturn(SOME_ARTICLES[0].publisher)

        val articles = articleMapper.mapFromRemote(SOME_ARTICLES_REMOTE)

        assertThat(articles).isEqualTo(SOME_ARTICLES)
    }

    @Test
    fun `should map from local`() {
        `when`(publisherMapperMock.map(SOME_ARTICLES_ENTITY[0].publisher)).thenReturn(SOME_ARTICLES[0].publisher)

        val articles = articleMapper.mapFromLocal(SOME_ARTICLES_ENTITY)

        assertThat(articles).isEqualTo(SOME_ARTICLES)
    }

    @Test
    fun `should map to local`() {
        `when`(publisherMapperMock.map(SOME_ARTICLES[0].publisher)).thenReturn(SOME_ARTICLES_ENTITY[0].publisher)
        val articleEntityExpected = SOME_ARTICLES_ENTITY[0].copy(isTop = false, searchQuery = "")
        val articlesEntityExpected = listOf(articleEntityExpected)

        val articlesEntity = articleMapper.mapToLocal(SOME_ARTICLES)

        assertThat(articlesEntity).isEqualTo(articlesEntityExpected)
    }

    @Test
    fun `should map article to articleEntity with isTop and searchQuery`() {
        val someArticle = SOME_ARTICLES[0]
        val articleEntityExpected = SOME_ARTICLES_ENTITY[0]
        val someIsTop = articleEntityExpected.isTop
        val someSearchQuery = articleEntityExpected.searchQuery
        `when`(publisherMapperMock.map(someArticle.publisher)).thenReturn(articleEntityExpected.publisher)

        val articleEntity = articleMapper.map(someArticle, someIsTop, someSearchQuery)

        assertThat(articleEntity).isEqualTo(articleEntityExpected)
    }
}