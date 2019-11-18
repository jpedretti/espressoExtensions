package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.UiController
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TopLevelFunctionsTest {

    @Test
    fun waitForGetDescription_ShouldReturnCorrectText() {
        val viewAction = waitFor(1000)

        val result = viewAction.description

        assertEquals("Wait for 1000 milliseconds.", result)
    }

    @Test
    fun waitForConstraint_ShouldReturnAssignableFromView() {
        val viewAction = waitFor(1000)

        val result = viewAction.constraints

        assertTrue(result is TypeSafeMatcher)
    }

    @Test
    fun waitForPerform_ShouldCall_UiController_LoopMainThreadUntilIdle() {
        val viewAction = waitFor(1000)
        val uiController = mockk<UiController>(relaxed = true)
        val view = mockk<View>()

        viewAction.perform(uiController, view)

        verify(exactly = 1) { uiController.loopMainThreadUntilIdle() }
    }

    @Test
    fun waitForPerform_ShouldCall_UiController_LoopMainThreadForAtLeast() {
        val viewAction = waitFor(1000)
        val uiController = mockk<UiController>(relaxed = true)
        val view = mockk<View>()

        viewAction.perform(uiController, view)

        verify(exactly = 1) { uiController.loopMainThreadForAtLeast(1000) }
    }
}