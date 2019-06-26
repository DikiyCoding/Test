package com.example.test.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.*
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.test.R
import com.example.test.ui.activities.*
import com.example.test.ui.adapters.HistoryAdapter.ViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTestSearchResultActivity {

    @get:Rule
    var menuActivityRule: ActivityTestRule<MenuActivity> =
        ActivityTestRule<MenuActivity>(MenuActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() = init()

    @Test
    @Throws(Exception::class)
    fun testSearchResultActivity() {
        //go to Search activity
        clickSearchButton()
        //create a search
        searchInitiate("java")
        //check the AppBar's title
        checkTitleAppBar()
        //check the articles list
        checkArticlesList()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() = release()

    private fun clearIntentPool() {
        release()
        init()
    }

    private fun clickSearchButton() {
        onView(withId(R.id.btn_search))
            .perform(click())
    }

    private fun searchInitiate(tag: String) {
        onView(withId(R.id.et_search))
            .perform(typeText(tag))
        onView(withId(R.id.btn_search))
            .perform(click())
    }

    private fun checkTitleAppBar() =
        onView(withId(R.id.tv_title))
            .check(matches(withText(R.string.articles)))

    private fun checkArticlesList() {
        clearIntentPool()
        onView(withId(R.id.list_articles))
            .check(matches(allOf(
                isFocusable(),
                withEffectiveVisibility(Visibility.VISIBLE))))
            .perform(scrollToPosition<ViewHolder>(32))
            .perform(scrollToPosition<ViewHolder>(16))
            .perform(scrollToPosition<ViewHolder>(8))
            .perform(scrollToPosition<ViewHolder>(1))
            .perform(actionOnItemAtPosition<ViewHolder>(5, click()))
        intended(hasComponent(DetailsActivity::class.java.name))
    }
}
