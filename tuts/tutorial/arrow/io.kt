package tutorial.arrow

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.left
import arrow.core.right
import arrow.fx.IO
import arrow.fx.extensions.fx
import kotlinx.coroutines.runBlocking

// https://arrow-kt.io/docs/0.11/effects/io/
// IO removed with 0.13 in favor of suspended functions; see: https://arrow-kt.io/docs/0.12/effects/io/
// https://lambda.show/blog/arrow-io-parallel
fun main() {
//    `beginner sample`()
//    `run unsafe`()
//    `effects io either`()
    `construct IO`()
}

// <editor-fold desc="beginner sample">
fun `beginner sample`() {
    val ioString: IO<String> = IO.just("1")
    val ioInt1: IO<Int> = ioString.map { it.toInt() } // preserve the IO wrapper
    val ioInt2: IO<Int> = ioString.flatMap { IO.just(it.toInt()) }


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

    val maybe: IO<Either<Throwable, Int>> = IO<Int> { throw Exception() }.attempt()
    maybe.unsafeRunSync().fold({ println("failed") }, { "success" })
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
    val ioResult = computingIo(true)
    println("running computingIo")
    // NEVER use unsafeRunSync()!!! except for tests/demos, but NEVER in production!
    val saferResult: Either<Throwable, Int> = ioResult.attempt().unsafeRunSync()
    println("safer result: $saferResult")
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

    println()

    val asyncResult: IO<Unit> = computingIo(true).runAsync {
        // need to return in here: IOOf<Unit>
        it.fold({ IO { println("async error") } }, { IO { println("async result: $it") } })
    }
    asyncResult.unsafeRunSync() // wait for it to finish

    // ... or: unsafeRunAsyncCancellable()
    // ... or: unsafeRunTimed
}

// </editor-fold>
// <editor-fold desc="effects">

fun `effects io either`() {
    data class Good2(val message2: String)

    fun fetchResult(): Either<Bad, Good> = Good("fetched result").right()
    fun Good.process(): Either<Bad, Good2> = Good2("yet again good").right()

    fun ioProgram(): IO<Either<Bad, Good2>> = IO.fx {
        val res = IO.effect { fetchResult() }.bind()
        res.fold({ IO.just(it.left()) }, { IO.effect { it.process() } }).bind()
    }


    // unwrap the IO into a common suspended
    suspend fun suspendedIoProgram(): Either<Bad, Good2> = ioProgram().suspended()
    runBlocking {
        suspendedIoProgram().fold({ println("fail") }, { println("success: $it") })
        println()
    }

    suspend fun suspendedEitherProgram(): Either<Bad, Good2> = either {
        val good = fetchResult().bind()
        val good2 = good.process().bind()
        good2
    }

    runBlocking {
        suspendedEitherProgram().fold({ println("fail") }, { println("success: $it") })
    }
}

// </editor-fold>
// <editor-fold desc="construction">
fun `construct IO`() {
    val justValue: IO<Int> = IO.just(1) // static value
    val errorValue: IO<Int> = IO.raiseError<Int>(Exception())
    val invokeValue: IO<Int> = IO { 2 } // invoked every time IO is run
    suspend fun suspendedFun() = 3
    // effect is like the invoke IO { }, but it accepts suspend functions
    val suspendedValue: IO<Int> = IO.effect { suspendedFun() } // invoked every time IO is run
    val deferredValue: IO<Int> = IO.defer { IO.just(4) }
    val asyncValue = IO.async<Int> { callback: (Either<Throwable, Int>) -> Unit ->
        println("asyncValue compute ...")
        callback(5.right())
//        callback(Exception().left())
        // could integreate other framework here
        // if forget to invoke callback => IO will run forver (consider using unsafeRunTimed!)
    }

    val value = asyncValue
    println("invoke1")
    value.attempt().unsafeRunSync().fold({ println("error1: $it") }, { println("success1: $it") })
    println("invoke2")
    value.attempt().unsafeRunSync().fold({ println("error2: $it") }, { println("success2: $it") })
}
// </editor-fold>

// IO.effect
// io.fold