package tutorial.kotest

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeTypeOf

class AssertionsTest : StringSpec({
    "some assertions" {
        // simple equals
        (2 + 2) shouldBe 4

        // chained strings
        "foo" shouldStartWith ("f") shouldContain ("o")

        // types
        val foo: Any = "foobar"
        foo.shouldBeTypeOf<String>() shouldContain "fo"

        // exceptions
        shouldThrow<Exception> {
            // failing test code
            throw Exception()
        }

    }
})
