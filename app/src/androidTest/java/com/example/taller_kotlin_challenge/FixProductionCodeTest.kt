package com.example.taller_kotlin_challenge

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FixProductionCodeTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.taller_kotlin_challenge", appContext.packageName)
    }

    /**
     * Task 3
     */
    @Test
    fun invalidLoginShowsError() {
        onView(withId(R.id.username)).perform(typeText("bad"))
        onView(withId(R.id.password)).perform(typeText("creds"))
        onView(withId(R.id.bt_login)).perform(click())
        onView(withId(R.id.error_text)).check(matches(isDisplayed()))
    }
}