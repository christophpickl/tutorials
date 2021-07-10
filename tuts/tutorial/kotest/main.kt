package tutorial.kotest

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec

/*
it is a...
1. test framework => https://kotest.io/docs/framework/framework.html
2. assertions lib (matchers)
3. property testing framework
*/

// TODO configuration (threads & co)
class MainKotestTest : DescribeSpec() {
    init {
        describe("main") {
            it("throw") {
                shouldThrow<Exception> {
                    throw Exception("")
                }
            }
        }
    }
}