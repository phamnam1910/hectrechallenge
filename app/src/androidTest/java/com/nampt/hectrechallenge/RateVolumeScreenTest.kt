package com.nampt.hectrechallenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RateVolumeScreenTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_UI_example() {
        Thread.sleep(3000L)
        onView(withText("Pruning")).check(matches(isDisplayed()))
        onView(withText("PIECE RATE")).perform(click())
        Thread.sleep(500L)
        onView(withText("APPLY TO ALL")).check(matches(isDisplayed()))
        onView(withText("WAGES")).perform(click())
        Thread.sleep(500L)
        onView(withText("Pruning will be paid by wages in this timesheet")).check(
            matches(
                isDisplayed()
            )
        )
    }
}