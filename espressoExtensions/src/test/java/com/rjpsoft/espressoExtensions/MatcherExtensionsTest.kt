package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.Espresso.onView
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.hamcrest.Matcher
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MatcherExtensionsTest {

    @Test
    fun onView_ShouldCallFunc() {
        val matcher = mockk<Matcher<View>>()
        var called = false

        matcher.onView {
            called = true
        }

        assertTrue(called)
    }

    @Test
    fun onView_ShouldCallAonView() {
        val matcher = mockk<Matcher<View>>()
        mockkStatic("androidx.test.espresso.Espresso")

        matcher.onView { }

        verify { onView(matcher) }
    }
}
