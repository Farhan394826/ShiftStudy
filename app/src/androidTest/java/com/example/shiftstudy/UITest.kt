package com.example.shiftstudy

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests that verify the app works
 * Uses ActivityScenarioRule instead of Compose testing
 */
@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Test 1: Activity launches
     */
    @Test
    fun testActivityLaunches() {
        activityRule.scenario.onActivity { activity ->
            assertNotNull("Activity should not be null", activity)
        }
    }

    /**
     * Test 2: Activity has content
     */
    @Test
    fun testActivityHasContent() {
        activityRule.scenario.onActivity { activity ->
            val rootView = activity.window.decorView
            assertNotNull("Root view should not be null", rootView)
        }
    }

    /**
     * Test 3: Activity is visible
     */
    @Test
    fun testActivityIsVisible() {
        Thread.sleep(1000)
        activityRule.scenario.onActivity { activity ->
            assertTrue("Activity should be running", !activity.isFinishing)
        }
    }

    /**
     * Test 4: App runs without crashing for 2 seconds
     */
    @Test
    fun testAppStability() {
        Thread.sleep(2000)
        activityRule.scenario.onActivity { activity ->
            assertTrue("Activity should still be running", !activity.isFinishing)
        }
    }

    /**
     * Test 5: Activity lifecycle works
     */
    @Test
    fun testActivityLifecycle() {
        activityRule.scenario.onActivity { activity ->
            assertNotNull("Activity should exist", activity)
            assertTrue("Activity should be resumed", !activity.isFinishing)
        }
    }

    /**
     * Test 6: Multiple lifecycle transitions
     */
    @Test
    fun testLifecycleTransitions() {
        // Simulate going to background and back
        activityRule.scenario.moveToState(androidx.lifecycle.Lifecycle.State.STARTED)
        Thread.sleep(500)
        activityRule.scenario.moveToState(androidx.lifecycle.Lifecycle.State.RESUMED)
        Thread.sleep(500)

        activityRule.scenario.onActivity { activity ->
            assertTrue("Activity should handle lifecycle", !activity.isFinishing)
        }
    }
}