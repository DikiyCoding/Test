package com.example.test.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
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
class UiTestDetailsActivity {

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
        //go to Details Activity
        clickItemArticlesList()
        //check the AppBar's title
        checkTitleAppBar()
        //check the WebView
        checkWebView()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() = release()

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

    private fun clickItemArticlesList() {
        onView(withId(R.id.list_articles))
            .perform(actionOnItemAtPosition<ViewHolder>(20, click()))
        intended(hasComponent(DetailsActivity::class.java.name))
    }

    private fun checkTitleAppBar() =
        onView(withId(R.id.tv_title))
            .check(matches(withText(R.string.article)))

    private fun checkWebView() {
        onView(withId(R.id.web_view_detail))
            .check(matches(allOf(
                isEnabled(),
                isClickable(),
                isFocusable(),
                withEffectiveVisibility(Visibility.VISIBLE))))
    }
}
