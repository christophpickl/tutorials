package tutorial.mockk

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

private val mock = mockk<SomeService>()

fun main() {
    val testee = Testee(mock)

    test1(testee)
    clearAllMocks() // without it, test2 will fail
    test2(testee)
}

private fun test1(testee: Testee) {
    every { mock.foo() } returns 11
    println("test1: ${testee.bar()}")
}

private fun test2(testee: Testee) {
    println("test2: ${testee.bar()}")
}

private class Testee(private val service: SomeService) {
    fun bar() = service.foo()
}

private interface SomeService {
    fun foo(): Int
}
