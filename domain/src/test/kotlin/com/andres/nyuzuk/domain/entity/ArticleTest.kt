package com.andres.nyuzuk.domain.entity

import org.junit.Test
import kotlin.test.assertEquals

class ArticleTest {
    private lateinit var article: Article

    @Test
    fun `should create`() {
        article = Article("title", "description", "content", "author", null, "imageUrl", "url")

        assertEquals("title", article.title)
    }
}