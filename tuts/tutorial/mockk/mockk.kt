package tutorial

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import mu.KLogger

// ABOUT: mockking in kotlin native style
// URL: https://mockk.io

fun main() {
    `mock calls`()
//    `capturing arguments`()
//    `capturing lambda arguments`()
}

fun `mock calls`() {
    class Service {
        fun greet(name: String) = "Hello $name!"
        fun unitReturning() {}
    }

    val mockedService = mockk<Service>()
    every { mockedService.greet("argument") } returns "mockedResult"
    every { mockedService.unitReturning() } just Runs // or make the mock more relaxed
    val result = mockedService.greet("argument")
    verify { mockedService.greet(any()) }
    result shouldBe "mockedResult"

    val repeatedAnswer = mockk<Service>()
    every { repeatedAnswer.greet(any()) } returns "a" andThen "b" // ... use `andThen`
    repeatedAnswer.greet("ignore") shouldBe "a"
    repeatedAnswer.greet("ignore") shouldBe "b"
}

fun `capturing arguments`() {
    data class Argument(val value: String)
    class Service {
        fun action(arg: Argument) = "Hello $arg"
    }

    val argSlot = slot<Argument>()
    val mockedService = mockk<Service>()
    every { mockedService.action(capture(argSlot)) } returns "mocked"

    mockedService.action(Argument("secret"))

    argSlot.captured.value shouldBe "secret"
}

fun `capturing lambda arguments`() {
    val logger = mockk<KLogger>()
    val message = slot<() -> Any?>()
    every { logger.error(msg = capture(message)) }

    logger.error("log message")

    message.captured() shouldBe "log message"
}
