package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import org.hamcrest.Matcher

fun Matcher<View>.onView(func: ViewInteraction.() -> Unit): ViewInteraction =
    onView(this).apply {
        func()
    }