![](https://github.com/jpedretti/espressoExtensions/workflows/Android%20Build/badge.svg) ![](https://github.com/jpedretti/espressoExtensions/workflows/Android%20Build%20And%20Publish/badge.svg)


# Espresso Extensions

#### A set of extensions functions to help writing Espresso Tests

Writing Espresso tests can be very verbose and with lots of code repetition. 

Even if you try to reduce code using the `allOf()` method for assertions and one of the assertions fail, the log won't be clear on which assertion failed.

The other problem is that when the tests are running is a modest machine some of them can fail because of some view that could not be rendered in time before the assertion executes.

```kotlin
onView(withId(R.id.mainEditText))
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText("View's text")
                    )
                )
            ).perform(click())
```

#### This set of extensions was created to solve those problems.

First, to get a view to check or act use the `onView` extension, which receives a function that will have a ViewInteraction as it's context so, all verifications and actions can be done in it.

Any function the returns a Matcher can be used with the onView extension.
```kt
withId(R.id.viewId).onView {
    //Verifications and Actions
}

allOf(withId(R.id.viewId), withText("text")).onView { }
```

#### Checking on the view

To check the view use the `verify` extension, which can receive as parameter a Matcher or a ViewAssertion;

With the verify and the onView extensions, it's possible to execute how many verifications you want and if one of them fails you will know exactly which one was.
```kotlin
withId(R.id.viewId).onView {
    verify(withText("View's Text"))
    verify(matches(isDisplayed()))
}
```

#### Performing actions on the view

To act on a view use the `act` extension, which receives as parameter a ViewAction. 

With the act and the onView extensions, it's possible to perform how many actions you want and if one of them fails you will know exactly which one was.
```kotlin
withId(R.id.viewId).onView {
    act(click())
}
```

It is possible to use both verify and act within the same onView call.

```kotlin
withId(R.id.viewId).onView {
    act(click())
    verify(withText("View's text after click"))
}
```

To prevent false test failure due to computer slowness both `verify` and `act` have a retry mechanism.

If an act or a verify fails, it will wait for 200 ms and then retry the act/verify. By default, they will do this 5 times and if after the fifth time it fails again, then your test will fail.

```kotlin
withId(R.id.mainEditText).onView {
    verify(matches(isDisplayed())) //will retry 5 times
    act(action = typeText("View's text after type"), retryCount = 1) //will retry one time
    verify(matcher = withText("View's text after type"), retryCount = 0) //won't retry
}
```
