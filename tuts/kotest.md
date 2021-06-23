kotlin testing: https://kotest.io/

# Infra

* different specifications available: `StringSpec`, `DescribeSpec`, `FunSpec`.

```kotlin
"skip a test".config(enabled = false) {
    // ...
}
```

# Asserts

```kotlin
// simple equals
(2 + 2) shouldBe 4

// chained strings
"foo" shouldStartWith ("f") shouldContain ("o")

// types
someValue.shouldBeTypeOf<String>() shouldContain "fo"

// exceptions
shouldThrow<MyException> {
    // failing test code
}

```