package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ViewInteractionExtensionHandlerTest {

    @Rule
    @JvmField
    var exceptionRule: ExpectedException = ExpectedException.none()
    
    //region matcherFailureHandler

    @Test
    fun matcherFailureHandler_WithNotAssertionFailedError_ShouldThrowError() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val matcherFailureHandler = viewInteraction.matcherFailureHandler(matcher, 1)
        val noMatchingViewException = mockk<NoMatchingViewException>()

        exceptionRule.expect(NoMatchingViewException::class.java)

        matcherFailureHandler.handle(noMatchingViewException, matcher)
    }

    @Test
    fun matcherFailureHandler_WithAssertionFailedErrorAndZeroRetries_ShouldThrowAssertionFailedError() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val matcherFailureHandler = viewInteraction.matcherFailureHandler(matcher, 0)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"

        exceptionRule.expect(AssertionFailedError::class.java)
        exceptionRule.expectMessage("no view found after retries")

        matcherFailureHandler.handle(assertionFailedError, matcher)
    }

    @Test
    fun matcherFailureHandler_WithAssertionFailedErrorAndOneRetries_ShouldCallVerifyWithZeroRetries() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val matcherFailureHandler = viewInteraction.matcherFailureHandler(matcher, 1)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        matcherFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.verify(matcher, 0) }
        verify(exactly = 1) { viewInteraction.verify(matcher = any(), retryCount = any()) }
    }

    @Test
    fun matcherFailureHandler_WithAssertionFailedErrorAndTwoRetries_ShouldCallVerifyWithOneRetry() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val matcherFailureHandler = viewInteraction.matcherFailureHandler(matcher, 2)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        matcherFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.verify(matcher, 1) }
        verify(exactly = 1) { viewInteraction.verify(matcher = any(), retryCount = any()) }
    }

    @Test
    fun matcherFailureHandler_WithAssertionFailedErrorAndRetriesDefaultValue_ShouldCallVerifyWith4Retries() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val matcherFailureHandler = viewInteraction.matcherFailureHandler(matcher)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        matcherFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.verify(matcher, 4) }
        verify(exactly = 1) { viewInteraction.verify(matcher = any(), retryCount = any()) }
    }

    @Test
    fun matcherFailureHandler_WithAssertionFailedError_ShouldCallPerformWithWaitFor200() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val matcherFailureHandler = viewInteraction.matcherFailureHandler(matcher)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")
        mockkStatic("com.rjpsoft.espressoExtensions.TopLevelFunctionsKt")

        matcherFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.perform(any()) }
        verify(exactly = 1) { waitFor(200) }
    }
    
    //endregion

    //region viewAssertionFailureHandler

    @Test
    fun viewAssertionFailureHandler_WithNotAssertionFailedError_ShouldThrowError() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val viewAssertion = mockk<ViewAssertion>()
        val viewAssertionFailureHandler = viewInteraction.viewAssertionFailureHandler(viewAssertion, 1)
        val noMatchingViewException = mockk<NoMatchingViewException>()

        exceptionRule.expect(NoMatchingViewException::class.java)

        viewAssertionFailureHandler.handle(noMatchingViewException, matcher)
    }

    @Test
    fun viewAssertionFailureHandler_WithAssertionFailedErrorAndZeroRetries_ShouldThrowAssertionFailedError() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val viewAssertion = mockk<ViewAssertion>()
        val viewAssertionFailureHandler = viewInteraction.viewAssertionFailureHandler(viewAssertion, 0)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"

        exceptionRule.expect(AssertionFailedError::class.java)
        exceptionRule.expectMessage("no view found after retries")

        viewAssertionFailureHandler.handle(assertionFailedError, matcher)
    }

    @Test
    fun viewAssertionFailureHandler_WithAssertionFailedErrorAndOneRetries_ShouldCallVerifyWithZeroRetries() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val viewAssertion = mockk<ViewAssertion>()
        val viewAssertionFailureHandler = viewInteraction.viewAssertionFailureHandler(viewAssertion, 1)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        viewAssertionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.verify(viewAssertion, 0) }
        verify(exactly = 1) { viewInteraction.verify(viewAssertion = any(), retryCount = any()) }
    }

    @Test
    fun viewAssertionFailureHandler_WithAssertionFailedErrorAndTwoRetries_ShouldCallVerifyWithOneRetry() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val viewAssertion = mockk<ViewAssertion>()
        val viewAssertionFailureHandler = viewInteraction.viewAssertionFailureHandler(viewAssertion, 2)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        viewAssertionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.verify(viewAssertion, 1) }
        verify(exactly = 1) { viewInteraction.verify(viewAssertion = any(), retryCount = any()) }
    }

    @Test
    fun viewAssertionFailureHandler_WithAssertionFailedErrorAndRetriesDefaultValue_ShouldCallVerifyWith4Retries() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val viewAssertion = mockk<ViewAssertion>()
        val viewAssertionFailureHandler = viewInteraction.viewAssertionFailureHandler(viewAssertion)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        viewAssertionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.verify(viewAssertion, 4) }
        verify(exactly = 1) { viewInteraction.verify(viewAssertion = any(), retryCount = any()) }
    }

    @Test
    fun viewAssertionFailureHandler_WithAssertionFailedError_ShouldCallPerformWithWaitFor200() {
        val viewInteraction = mockk<ViewInteraction>()
        val matcher = mockk<Matcher<View>>()
        val viewAssertion = mockk<ViewAssertion>()
        val viewAssertionFailureHandler = viewInteraction.viewAssertionFailureHandler(viewAssertion)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")
        mockkStatic("com.rjpsoft.espressoExtensions.TopLevelFunctionsKt")

        viewAssertionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.perform(any()) }
        verify(exactly = 1) { waitFor(200) }
    }

    //endregion

    //region viewActionFailureHandler

    @Test
    fun viewActionFailureHandler_WithNotAssertionFailedError_ShouldThrowError() {
        val viewInteraction = mockk<ViewInteraction>()
        val viewAction = mockk<ViewAction>()
        val matcher = mockk<Matcher<View>>()
        val viewActionFailureHandler = viewInteraction.viewActionFailureHandler(viewAction, 1)
        val noMatchingViewException = mockk<NoMatchingViewException>()

        exceptionRule.expect(NoMatchingViewException::class.java)

        viewActionFailureHandler.handle(noMatchingViewException, matcher)
    }

    @Test
    fun viewActionFailureHandler_WithAssertionFailedErrorAndZeroRetries_ShouldThrowAssertionFailedError() {
        val viewInteraction = mockk<ViewInteraction>()
        val viewAction = mockk<ViewAction>()
        val matcher = mockk<Matcher<View>>()
        val viewActionFailureHandler = viewInteraction.viewActionFailureHandler(viewAction, 0)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"

        exceptionRule.expect(AssertionFailedError::class.java)
        exceptionRule.expectMessage("no view found after retries")

        viewActionFailureHandler.handle(assertionFailedError, matcher)
    }

    @Test
    fun viewActionFailureHandler_WithAssertionFailedErrorAndOneRetries_ShouldCallVerifyWithZeroRetries() {
        val viewInteraction = mockk<ViewInteraction>()
        val viewAction = mockk<ViewAction>()
        val matcher = mockk<Matcher<View>>()
        val viewActionFailureHandler = viewInteraction.viewActionFailureHandler(viewAction, 1)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        viewActionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.act(action = viewAction, retryCount = 0) }
        verify(exactly = 1) { viewInteraction.act(action = any(), retryCount = any()) }
    }

    @Test
    fun viewActionFailureHandler_WithAssertionFailedErrorAndTwoRetries_ShouldCallVerifyWithOneRetry() {
        val viewInteraction = mockk<ViewInteraction>()
        val viewAction = mockk<ViewAction>()
        val matcher = mockk<Matcher<View>>()
        val viewActionFailureHandler = viewInteraction.viewActionFailureHandler(viewAction, 2)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        viewActionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.act(viewAction, 1) }
        verify(exactly = 1) { viewInteraction.act(action = any(), retryCount = any()) }
    }

    @Test
    fun viewActionFailureHandler_WithAssertionFailedErrorAndRetriesDefaultValue_ShouldCallVerifyWith4Retries() {
        val viewInteraction = mockk<ViewInteraction>()
        val viewAction = mockk<ViewAction>()
        val matcher = mockk<Matcher<View>>()
        val viewActionFailureHandler = viewInteraction.viewActionFailureHandler(viewAction)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")

        viewActionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 1) { viewInteraction.act(viewAction, 4) }
        verify(exactly = 1) { viewInteraction.act(action = any(), retryCount = any()) }
    }

    @Test
    fun viewActionFailureHandler_WithAssertionFailedError_ShouldCallPerformWithWaitFor200() {
        val viewInteraction = mockk<ViewInteraction>()
        val viewAction = mockk<ViewAction>()
        val matcher = mockk<Matcher<View>>()
        val viewActionFailureHandler = viewInteraction.viewActionFailureHandler(viewAction)
        val assertionFailedError = mockk<AssertionFailedError>()
        every { assertionFailedError.message } returns "no view found"
        every { viewInteraction.perform(any()) } returns viewInteraction
        every { viewInteraction.withFailureHandler(any()) } returns viewInteraction
        every { viewInteraction.check(any()) } returns viewInteraction
        mockkStatic("com.rjpsoft.espressoExtensions.ViewInteractionExtensionsKt")
        mockkStatic("com.rjpsoft.espressoExtensions.TopLevelFunctionsKt")

        viewActionFailureHandler.handle(assertionFailedError, matcher)

        verify(exactly = 2) { viewInteraction.perform(any()) }
        verify(exactly = 1) { waitFor(200) }
    }

    //endregion
}