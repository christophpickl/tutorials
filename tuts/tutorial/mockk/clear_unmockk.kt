@file:JvmName("clear_unmockk")

package tutorial.mockk

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll

fun main() {
    val tests = listOf(::test1, ::test2)

    tests.forEach {
        it.invoke()
//        clearAllMocks() // ... keeps the mock, just resets it! delegating to real impl though ;)
        unmockkAll() // ... back to real impl
    }
}

fun Int.foo(): Int {
    println("real $this.foo() invoked")
    return 1
}


private fun test1() {
    mockkStatic("tutorial.mockk.clear_unmockk")
    every { any<Int>().foo() } returns 99
    println("test1: ${1.foo()}")

}

private fun test2() {
    println("test2: ${2.foo()}")
}
