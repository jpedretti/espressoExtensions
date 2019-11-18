package com.rjpsoft.espressoextensions.demoapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rjpsoft.espressoExtensions.act
import com.rjpsoft.espressoExtensions.onView
import com.rjpsoft.espressoExtensions.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExtensionsTest {

    @Test
    fun verify_WithMatcher() {
        ActivityScenario.launch(MainActivity::class.java)

        withText("Hello World!").onView {
            verify(isDisplayed())
        }
    }

    @Test
    fun act() {
        ActivityScenario.launch(MainActivity::class.java)

        withId(R.id.mainEditText).onView {
            act(typeText("Another Hello World!"))
        }
    }

    @Test
    fun verify_WithViewAssertion() {
        ActivityScenario.launch(MainActivity::class.java)

        withId(R.id.mainEditText).onView {
            act(typeText("Another Hello World!"))

            verify(matches(withText("Another Hello World!")))
        }
    }
}
