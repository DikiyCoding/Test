package com.example.test.activities

import android.support.test.espresso.Espresso
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
class UiTestSearchActivity {

    @get:Rule
    var menuActivityRule: ActivityTestRule<MenuActivity> =
        ActivityTestRule<MenuActivity>(MenuActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() = init()

    @Test
    @Throws(Exception::class)
    fun testSearchActivity() {
        //go to Search activity
        clickSearchButton()
        //fill the history list
        fillList()
        //check AppBar's title
        checkTitleAppBar()
        //check EditText
        checkEditText("ide")
        //check Button Search
        checkButtonSearch()
        //check the view which is being showed when the list is empty
        checkEmptyListView()
        //check the tags list
        checkTagsList()
        //check the history list
        checkHistoryList()
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

    private fun fillList() {
        addItem("java")
        addItem("scala")
        addItem("ruby")
        addItem("vkontakte")
    }

    private fun addItem(tag: String) {
        onView(withId(R.id.et_search))
            .perform(
                clearText(),
                typeText(tag)
            )
        onView(withId(R.id.btn_search))
            .perform(click())
        Espresso.pressBack()
    }

    private fun checkTitleAppBar() =
        onView(withId(R.id.tv_title))
            .check(matches(withText(R.string.search)))

    private fun checkEditText(tag: String) {
        onView(withId(R.id.et_search))
            .check(matches(allOf(
                withHint(R.string.et_search_hint),
                withEffectiveVisibility(Visibility.VISIBLE),
                isEnabled(),
                isClickable(),
                isFocusable())))
            .perform(
                clearText(),
                typeText(tag)
            )
    }

    private fun checkButtonSearch() {
        clearIntentPool()
        onView(withId(R.id.btn_search))
            .check(matches(allOf(
                withText(R.string.find),
                withEffectiveVisibility(Visibility.VISIBLE),
                isClickable(),
                isFocusable())))
            .perform(click())
        intended(hasComponent(SearchResultActivity::class.java.name))
        Espresso.pressBack()
    }

    private fun checkEmptyListView() {
        onView(withId(R.id.iv_list_empty))
            .check(matches(
                withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.tv_list_empty))
            .check(matches(allOf(
                withText(R.string.tv_list_empty),
                withEffectiveVisibility(Visibility.GONE))))
    }

    private fun checkTagsList() {
        clearIntentPool()
        onView(withId(R.id.list_tags))
            .check(matches(allOf(
                isFocusable(),
                withEffectiveVisibility(Visibility.VISIBLE))))
            .perform(scrollToPosition<ViewHolder>(16))
            .perform(scrollToPosition<ViewHolder>(8))
            .perform(scrollToPosition<ViewHolder>(1))
            .perform(actionOnItemAtPosition<ViewHolder>(4, click()))
        intended(hasComponent(SearchResultActivity::class.java.name))
        Espresso.pressBack()
    }

    private fun checkHistoryList() {
        clearIntentPool()
        onView(withId(R.id.list_history))
            .check(matches(allOf(
                isFocusable(),
                withEffectiveVisibility(Visibility.VISIBLE))))
            .perform(scrollToPosition<ViewHolder>(4))
            .perform(scrollToPosition<ViewHolder>(2))
            .perform(scrollToPosition<ViewHolder>(1))
            .perform(scrollToPosition<ViewHolder>(3))
            .perform(actionOnItemAtPosition<ViewHolder>(1, click()))
        intended(hasComponent(SearchResultActivity::class.java.name))
    }
}
