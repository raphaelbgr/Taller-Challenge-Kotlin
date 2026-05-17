package com.example.taller_kotlin_challenge

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicInteger

/**
 * Instrumented tests that run on an Android device or emulator.
 */
@RunWith(AndroidJUnit4::class)
class FixProductionCodeTest {

    private lateinit var scenario: ActivityScenario<TallerCodeChallengeTestActivity>

    @Before
    fun setUp() {
        Espresso.registerIdlingResources(TestIdlingResource.countingIdlingResource)
        scenario = ActivityScenario.launch(TallerCodeChallengeTestActivity::class.java)
    }

    @After
    fun tearDown() {
        Espresso.unregisterIdlingResources(TestIdlingResource.countingIdlingResource)
        scenario.close()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.taller_kotlin_challenge", appContext.packageName)
    }

    /**
     * Task 3 — invalid credentials must reveal the error view.
     */
    @Test
    fun invalidLoginShowsError() {
        onView(withId(R.id.username)).perform(typeText("bad"))
        onView(withId(R.id.password)).perform(typeText("creds"))
        onView(withId(R.id.bt_login)).perform(click())
        onView(withId(R.id.error_text)).check(matches(isDisplayed()))
    }
}

/**
 * Global idling resource for Espresso synchronisation with asynchronous operations.
 */
object TestIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

class SimpleCountingIdlingResource(
    private val resourceName: String
) : IdlingResource {

    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = resourceName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(
        resourceCallback: IdlingResource.ResourceCallback
    ) {
        this.resourceCallback = resourceCallback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            resourceCallback?.onTransitionToIdle()
        } else if (counterVal < 0) {
            throw IllegalStateException("Counter has been corrupted!")
        }
    }
}
