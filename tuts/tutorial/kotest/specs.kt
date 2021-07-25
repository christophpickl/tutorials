package tutorial.kotest

import io.kotest.assertions.arrow.validation.shouldBeValid
import io.kotest.core.script.context
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.spec.style.describeSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldNotBeZero
import io.kotest.matchers.shouldBe


class StringSimpleTest : StringSpec({
    fun fooBar() {}
    "string test 1" {
        println("test 1")
        fooBar()
    }
    "skipped test".config(enabled = false) {
        println("skipped test")
    }
})

class StringComplexTest : StringSpec() {
    fun fooBar() {}

    init {
        "init test" {
            println("string test")
            fooBar()
        }
    }
}

class WordTest : WordSpec() {
    init {
        "outer test" should {
            "inner test" { // nested test
                println("word test")
            }
        }
    }
}

class DescribeTest : DescribeSpec({
    describe("outer test") {
        it("inner test") {
            println("test")
        }
    }

    fun dynamicTest(prefix: Int) = describeSpec {
        describe("dynamic outer $prefix") {
            it("dynamic inner $prefix") {
                println("running test prefix $prefix")
            }
        }
    }

    context("super context") {
        context("sub context") {
            describe("describe") {
                it("it") {
                    println("context test")
                }
            }
        }
    }
    // include predefined tests
    include(dynamicTest(1))
    include(dynamicTest(2))
})

class FunTest : FunSpec({
    listOf(1, 2, 3).forEach {
        test("Test #$it") {
            it.shouldNotBeZero()
        }
    }
    // will break the build! nice ;)
//    xtest("ignored") {
//        println("skipped")
//    }
    context("myContextB") {
        test("contexted test") {
            println("context test run")
        }
    }
})