package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ViewInteractionExtensionsTest {

    @Test
    fun verifyMatcher_WithSuccess_ShouldCallCheckOneTime() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val matcher = mockk<Matcher<View>>()
        every { viewInteraction.check(any()) } returns viewInteraction

        viewInteraction.verify(matcher)

        verify(exactly = 1) { viewInteraction.check(any()) }
    }

    @Test
    fun verifyMatcher_WithDefaultRetryValue_ShouldSetFailureHandlerWith5Retries() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val matcher = mockk<Matcher<View>>()
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionHandlerKt")

        viewInteraction.verify(matcher)

        verify { viewInteraction.withFailureHandler(any())}
        verify { viewInteraction.matcherFailureHandler(matcher, 5) }
    }

    @Test
    fun verifyMatcher_WithZeroRetries_ShouldSetFailureHandlerWith0Retries() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val matcher = mockk<Matcher<View>>()
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionHandlerKt")

        viewInteraction.verify(matcher, 0)

        verify { viewInteraction.withFailureHandler(any())}
        verify { viewInteraction.matcherFailureHandler(matcher, 0) }
    }

    @Test
    fun verifyViewAssertion_WithSuccess_ShouldCallCheckOneTime() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val viewAssertion = mockk<ViewAssertion>()
        every { viewInteraction.check(any()) } returns viewInteraction

        viewInteraction.verify(viewAssertion)

        verify(exactly = 1) { viewInteraction.check(viewAssertion) }
    }

    @Test
    fun verifyViewAssertion_WithDefaultRetryValue_ShouldSetFailureHandlerWith5Retries() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val viewAssertion = mockk<ViewAssertion>()
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionHandlerKt")

        viewInteraction.verify(viewAssertion)

        verify { viewInteraction.viewAssertionFailureHandler(viewAssertion, 5) }
    }

    @Test
    fun verifyViewAssertion_WithZeroRetries_ShouldSetFailureHandlerWith0Retries() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val viewAssertion = mockk<ViewAssertion>()
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionHandlerKt")

        viewInteraction.verify(viewAssertion, 0)

        verify { viewInteraction.viewAssertionFailureHandler(viewAssertion, 0) }
    }

    @Test
    fun act_WithSuccess_ShouldCallCheckOneTime() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val viewAction = mockk<ViewAction>()
        every { viewInteraction.check(any()) } returns viewInteraction

        viewInteraction.act(viewAction)

        verify(exactly = 1) { viewInteraction.perform(viewAction) }
    }

    @Test
    fun act_ShouldSetFailureHandler() {
        val viewInteraction = mockk<ViewInteraction>(relaxUnitFun = true, relaxed = true)
        val viewAction = mockk<ViewAction>()
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionHandlerKt")

        viewInteraction.act(viewAction)

        verify { viewInteraction.viewActionFailureHandler(viewAction, 5) }
    }
}