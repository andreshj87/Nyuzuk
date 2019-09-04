package com.andres.nyuzuk.presentation.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.andres.nyuzuk.presentation.UnitTest
import com.andres.nyuzuk.presentation.dummyfactory.ArticleDummyFactory
import org.junit.Test
import org.mockito.Mock

class ArticleUiMapperTest: UnitTest() {
    private val SOME_ARTICLE = ArticleDummyFactory.createArticle()
    private val SOME_ARTICLES = listOf(SOME_ARTICLE)
    private val SOME_ARTICLE_UI = ArticleDummyFactory.createArticleUi()
    private val SOME_ARTICLES_UI = listOf(SOME_ARTICLE_UI)

    private lateinit var articleUiMapper: ArticleUiMapper

    @Mock private lateinit var publisherUiMapperMock: PublisherUiMapper

    override fun onSetup() {
        articleUiMapper = ArticleUiMapper(publisherUiMapperMock)
    }

    @Test
    fun `should map articles into articlesUi`() {
        val articlesUiExpected = SOME_ARTICLES_UI

        val articlesUi = articleUiMapper.map(SOME_ARTICLES)

        assertThat(articlesUi).isEqualTo(articlesUiExpected)
    }
}