package com.example.voting

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class loginUI {
    @get:Rule
    val  testRule = ActivityScenarioRule(login::class.java)

    @Test
    fun testLoginUI(){
        onView(withId(R.id.cts))
            .perform(typeText("0987"))

        onView(withId(R.id.paw))
            .perform(typeText("nishchit"))

        closeSoftKeyboard()

        onView(withId(R.id.submit))
            .perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.passport))
            .check(matches(isDisplayed()))
    }
}