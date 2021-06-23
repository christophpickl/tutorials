package tutorial.arrow

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.fx.IO
import arrow.fx.extensions.fx
import kotlinx.coroutines.runBlocking

// https://arrow-kt.io/docs/0.12/effects/io/
// https://lambda.show/blog/arrow-io-parallel
fun main() {
//    `beginner sample`()
//    `run unsafe`()
    `effects io either`()
}

// <editor-fold desc="beginner sample">
fun `beginner sample`() {
    fun someNumber(): IO<Int> = IO.just(21)
    fun triple(): IO<Triple<Int, Int, Int>> =
        someNumber().flatMap { a ->
            someNumber().flatMap { b ->
                someNumber().map { c ->
                    Triple(a, b, c)
                }
            }
        }
    println(triple().unsafeRunSync())
}

// </editor-fold>
// <editor-fold desc="computation via run unsafe">


fun `run unsafe`() {
    fun computingIo(fail: Boolean): IO<Int> = IO {
        println("computingIo running ... (fail=$fail)")
        if (fail) throw Exception("failed")
        42
    }

    println("calling computingIo")
    val ioResult = computingIo(false)
    println("running computingIo")
    val result: Int = ioResult.unsafeRunSync() // will throw, if fail=true
    println("result: $result")

    println()

    println("calling computingIo failing")
    val ioResultFail = computingIo(true)
    println("running computingIo failing async unsafe...")
    // transform an IO<A> into an Either<Throwable, A>
    ioResultFail.unsafeRunAsync { either: Either<Throwable, Int> ->
        either.fold({ println("error: ${it.message}") }, { println("result: $it") })
    }
}

// </editor-fold>
// <editor-fold desc="effects">

fun `effects io either`() {
    data class Good2(val message2: String)

    fun fetchResult(): Either<Bad, Good> = Good("fetched result").right()
    fun Good.process(): Either<Bad, Good2> = Good2("yet again good").right()

    fun ioProgram(): IO<Either<Bad, Good2>> = IO.fx {
        val res = !IO.effect { fetchResult() }
        !res.fold({ IO.just(it.left()) }, { IO.effect { it.process() } })
    }

    suspend fun suspendedIoProgram() = ioProgram().suspended()

    runBlocking {
        suspendedIoProgram().fold({ println("fail") }, { println("success: $it") })
    }
}

// </editor-fold>

// TODO
// IO.effect
// IO.just
// io.fold