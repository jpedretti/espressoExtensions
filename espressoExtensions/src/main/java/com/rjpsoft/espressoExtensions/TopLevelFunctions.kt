package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun waitFor(millis: Long) = object : ViewAction {
    override fun getDescription() = "Wait for $millis milliseconds."

    override fun getConstraints(): Matcher<View> = ViewMatchers.isAssignableFrom(View::class.java)

    override fun perform(uiController: UiController?, view: View?) {
        uiController?.loopMainThreadUntilIdle()
        uiController?.loopMainThreadForAtLeast(millis)
    }
}