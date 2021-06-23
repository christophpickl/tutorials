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
//    `simple setup`()
//    `capturing arguments`()
    `capturing lambda arguments`()
}

fun `simple setup`() {
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
}

fun `capturing arguments`() {
    data class Argument(val value: String)
    class Service {
        fun action(arg: Argument) = 21
    }

    val argSlot = slot<Argument>()
    val mockedService = mockk<Service>()
    every { mockedService.action(capture(argSlot)) } returns 42

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
