package com.example.test.activities

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.test.R
import com.example.test.ui.activities.HistoryActivity
import com.example.test.ui.activities.MenuActivity
import com.example.test.ui.activities.SearchActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTestMenuActivity {

    @get:Rule
    var menuActivityRule: ActivityTestRule<MenuActivity> =
        ActivityTestRule<MenuActivity>(MenuActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() = init()

    @Test
    @Throws(Exception::class)
    fun testTitleAppBar() {
        onView(withId(R.id.tv_title))
            .check(matches(withText(R.string.menu)))
    }

    @Test
    @Throws(Exception::class)
    fun testButtonSearch() {
        onView(withId(R.id.btn_search))
            .check(matches(allOf(
                withText(R.string.search),
                withEffectiveVisibility(Visibility.VISIBLE),
                isClickable(),
                isFocusable())))
            .perform(click())
        intended(hasComponent(SearchActivity::class.java.name))
    }

    @Test
    @Throws(Exception::class)
    fun testButtonHistory() {
        onView(withId(R.id.btn_history))
            .check(matches(allOf(
                withText(R.string.history),
                withEffectiveVisibility(Visibility.VISIBLE),
                isClickable(),
                isFocusable())))
            .perform(click())
        intended(hasComponent(HistoryActivity::class.java.name))
    }

    @After
    @Throws(Exception::class)
    fun tearDown() = release()
}
