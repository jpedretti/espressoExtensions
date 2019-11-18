package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher

internal fun ViewInteraction.matcherFailureHandler(
    matcher: Matcher<View>,
    retryCount: Int = 5
) = FailureHandler { error, _ ->
    when {
        error !is AssertionFailedError -> throw error
        retryCount > 0 -> {
            perform(waitFor(200))
            verify(matcher, retryCount - 1)
        }
        else -> throw AssertionFailedError("${error.message} after retries")
    }
}

internal fun ViewInteraction.viewAssertionFailureHandler(
    viewAssertion: ViewAssertion,
    retryCount: Int = 5
) = FailureHandler { error, _ ->
    when {
        error !is AssertionFailedError -> throw error
        retryCount > 0 -> {
            perform(waitFor(200))
            verify(viewAssertion, retryCount - 1)
        }
        else -> throw AssertionFailedError("${error.message} after retries")
    }
}

internal fun ViewInteraction.viewActionFailureHandler(
    action: ViewAction,
    retryCount: Int = 5
) = FailureHandler { error, _ ->
    when {
        error !is AssertionFailedError -> throw error
        retryCount > 0 -> {
            perform(waitFor(200))
            act(action, retryCount - 1)
        }
        else -> throw AssertionFailedError("${error.message} after retries")
    }
}