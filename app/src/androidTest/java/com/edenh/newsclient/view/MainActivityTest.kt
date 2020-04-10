package com.edenh.newsclient.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.edenh.newsclient.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun test_isListLoaded() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.articles_list))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeDown())
    }

}