package com.rjpsoft.espressoExtensions

import android.view.View
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matcher
import java.security.InvalidParameterException

fun ViewInteraction.verify(
    matcher: Matcher<View>,
    retryCount: Int = 5
): ViewInteraction {
    if (retryCount < 0) throw InvalidParameterException("retryCount must be greater or equal to zero")
    val handler = matcherFailureHandler(matcher, retryCount)
    withFailureHandler(handler)
    return check(matches(matcher))
}

fun ViewInteraction.verify(
    viewAssertion: ViewAssertion,
    retryCount: Int = 5
): ViewInteraction {
    if (retryCount < 0) throw InvalidParameterException("retryCount must be greater or equal to zero")
    withFailureHandler(viewAssertionFailureHandler(viewAssertion, retryCount))
    return check(viewAssertion)
}

fun ViewInteraction.act(
    action: ViewAction,
    retryCount: Int = 5
): ViewInteraction {
    if (retryCount < 0) throw InvalidParameterException("retryCount must be greater or equal to zero")
    withFailureHandler(viewActionFailureHandler(action, retryCount))
    return perform(action)
}