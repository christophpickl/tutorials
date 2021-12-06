package tutorial.kotest

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

// see: https://kotlintesting.com/kotest-parameterized/

class ParametrizedTest : DescribeSpec() {
    init {
        describe("parametrized test sample") {
            forAll(
                row(1, "1"),
                row(2, "2"),
            ) { nr: Int, expected: String ->
                println("nr: $nr")
                nr.toString() shouldBe expected
            }
        }
    }
}
