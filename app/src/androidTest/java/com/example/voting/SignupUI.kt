package com.example.voting

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class SignupUI {
    @get:Rule
    val testRule = ActivityScenarioRule(signup::class.java)

    @Test
    fun testSignupUI(){
        onView(withId(R.id.Frt))
            .perform(typeText("renish"))

        onView(withId(R.id.lat))
            .perform(typeText("shah"))

        onView(withId(R.id.eml))
            .perform(typeText("asdasd@"))
        closeSoftKeyboard()

        onView(withId(R.id.ctz))
            .perform(typeText("878"))
        closeSoftKeyboard()

        onView(withId(R.id.phn))
            .perform(typeText("98798"))
        closeSoftKeyboard()

        onView(withId(R.id.pws))
            .perform(typeText("anish"))
        closeSoftKeyboard()

        onView(withId(R.id.rpws))
            .perform(typeText("anish"))

        closeSoftKeyboard()

        onView(withId(R.id.sgn))
            .perform(ViewActions.click())


        Thread.sleep(2000)

        onView(withId(R.id.pws))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}