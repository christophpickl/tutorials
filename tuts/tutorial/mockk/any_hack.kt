package tutorial.mockk

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.mockk.ConstantMatcher
import io.mockk.MockKException
import io.mockk.MockKGateway
import io.mockk.MockKMatcherScope
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlin.reflect.KClass

/*
value (as well as inline) classes are not (yet) supported by mockk.
usually we would do service.method(any()), but with those we have to do sevice.method(Dto(any())) instead.
to workaround this, a simple enhancement to the any() to detect these cases and do some reflection magic.

play around by enabling different versions of the Dto, and see all tests passing :)
 */

//data
//inline
@JvmInline
value
class Dto(
    val someProperty: String = "someValue"
//    val someProperty: Int = 42
)

interface Service {
    fun consume(dto: Dto)
}

class InlineClassTest : StringSpec() {
    init {
        lateinit var service: Service

        beforeEach {
            service = mockk()
        }
        println("Dto is data class: ${Dto::class.isData}")
        if (!Dto::class.isData) {
            "every any() => FAIL" {
                shouldThrow<MockKException> {
                    every { service.consume(any()) } just Runs
                }
            }
            "every Dto(any()) => OK" {
                shouldNotThrow<Throwable> {
                    every { service.consume(Dto(any())) } just Runs
                }
            }
            "verify any() => FAIL" {
                every { service.consume(Dto(any())) } just Runs
                service.consume(Dto())

                shouldThrow<MockKException> {
                    verify(exactly = 1) { service.consume(any()) }
                }
            }
            "verify Dto(any()) => OK" {
                every { service.consume(Dto(any())) } just Runs
                service.consume(Dto())

                shouldNotThrow<Throwable> {
                    verify(exactly = 1) { service.consume(Dto(any())) }
                }
            }
        }

        "every anyX() => OK" {
            shouldNotThrow<Throwable> {
                every { service.consume(anyX()) } just Runs
            }
        }
        "verify anyX() => OK" {
            every { service.consume(anyX()) } just Runs
            service.consume(Dto())

            shouldNotThrow<Throwable> {
                verify(exactly = 1) { service.consume(anyX()) }
            }
        }
    }
}

/** use to transparently switch between data/inline/value classes without bothering with mockk's weirdness */
inline fun <reified T> MockKMatcherScope.anyX(): T = when {
    T::class.isData -> any()
    else -> anyXForInlineOrValue()
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> MockKMatcherScope.anyXForInlineOrValue(): T {
    val ctor = T::class.constructors.first()
    val param = ctor.parameters.first()
    val paramType = param.type.classifier as KClass<Any>
    val mockedArg = callRecorder.matcher(ConstantMatcher<Any>(true), paramType)
    return ctor.call(mockedArg)
}

val MockKMatcherScope.callRecorder: MockKGateway.CallRecorder
    get() = MockKMatcherScope::class.java.getDeclaredField("callRecorder").let {
        it.isAccessible = true
        it.get(this) as MockKGateway.CallRecorder
    }
