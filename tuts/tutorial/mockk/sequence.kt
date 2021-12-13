package tutorial.mockk

import io.mockk.every
import io.mockk.mockk

private interface Foo {
    fun bar(): String
}

fun main() {
    val foo = mockk<Foo>() {
        every { bar() } returnsMany listOf("1", "2")
    }

    println("#1: ${foo.bar()}") // 1
    println("#2: ${foo.bar()}") // 2
    println("#3: ${foo.bar()}") // 2
}