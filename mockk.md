mockking in kotlin native style.

# Basics

simple setup:

```kotlin
val foo = mockk<MyType>()
every { foo.action(42) } returns "universe"
// ... some logic ...
verify { foo.action(any()) }
```

# Capture

Lambdas:

```kotlin
val logger = mockk<Logger>()
val message = slot<() -> Any?>()
every { logger.error( msg = capture(message)) }

message.captured() shouldBe "foo bar"
```