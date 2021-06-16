mockking in kotlin native style: https://mockk.io/

# Basics

simple setup:

```kotlin
val foo = mockk<MyType>()
every { foo.action(42) } returns "universe"
// ... some logic ...
verify { foo.action(any()) }
```

special cases:

```kotlin
// function returning unit, simply run it
every { foo.action() } just Runs
```

# Capture

Capture values:

```kotlin
val fooSlot = slot<Foo>()
every { service.action(capture(fooSlot)) } returns something
// ...
fooSlot.captured.member shouldBe "bar"
```

Lambdas:

```kotlin
val logger = mockk<Logger>()
val message = slot<() -> Any?>()
every { logger.error(msg = capture(message)) }

message.captured() shouldBe "foo bar"
```
