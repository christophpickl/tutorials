kotlin testing.

# Infra

* different specifications available: `StringSpec`, `DescribeSpec`, `FunSpec`.

# Asserts

```kotlin
(2 + 2) shouldBe 4
"foo" shouldStartWith ("f") shouldContain ("o")
someValue.shouldBeTypeOf<String>() shouldContain "fo"
```