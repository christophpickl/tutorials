package tutorial.kotest

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.WordSpec


// TODO list all specs:
// StringSpec
// WordSpec
// DescribeSpec
// FunSpec

class MostSimpleStringTest : StringSpec({
    fun fooBar() {}
    "test 1" {
        println("test 1")
        fooBar()
    }
    "skipped test".config(enabled = false) {
        println("skipped test")
    }
})

class OtherStringTest : StringSpec() {
    fun fooBar() {}
    init {
        "test 1" {
            println("test 1")
            fooBar()
        }
    }
}

class OtherHookTest : WordSpec() {
    init {
        "this test" should {
            "be alive" { // nested test
                println("alive test")
            }
        }
    }
}
