package com.andres.nyuzuk.presentation.features.search

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.andres.nyuzuk.presentation.InstrumentationTest
import org.junit.Test


class ArticleSearchActivityInstrumentationTest : InstrumentationTest<ArticleSearchViewState, ArticleSearchViewModel, ArticleSearchActivity>() {
    override fun getClassUnderTest() = ArticleSearchActivity::class.java

    @Test
    fun shouldNavigateToFirstArticleDetailAfterSearch() {
        onView(withId(com.andres.nyuzuk.R.id.action_search)).perform(click())

        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("technology"));

        SystemClock.sleep(3000)
        onView(withId(com.andres.nyuzuk.R.id.recyclerview_search_articles)).perform(RecyclerViewActions.actionOnItemAtPosition<ArticleSearchAdapter.SearchArticleViewHolder>(0, click()))
    }
}