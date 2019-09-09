package com.andres.nyuzuk.presentation.features.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.getIntents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.truth.content.IntentSubject.assertThat
import com.andres.nyuzuk.R
import com.andres.nyuzuk.presentation.InstrumentationTest
import com.andres.nyuzuk.presentation.features.search.ArticleSearchActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentationTest : InstrumentationTest<MainViewState, MainViewModel, MainActivity>() {
    override fun getClassUnderTest() = MainActivity::class.java

    @Test
    fun shouldDisplayTopArticlesScreenWhenMainActivityIsLoaded() {
        onView(withId(R.id.recyclerview_top_articles)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToSearchActivityWhenTappingOnSearchFloatingButton() {
        onView(withId(R.id.fab_search)).perform(click())

        assertThat(getIntents().first()).hasComponentClass(ArticleSearchActivity::class.java.name)
    }
}