package tutorial.arrow

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import kotlinx.coroutines.runBlocking

// model domain errors as clearly as possible: Either<DomainError, SuccessValue>

fun main() {
//    `sample either`()
//    `map left and right`()
//    `catch exceptions`()
}


// <editor-fold desc="Initial Sample">
fun `sample either`() {
    fun ageCheck(age: Int): Either<Bad, Good> {
        println("ageCheck...")
        return if (age < 18) Bad("too young").left() else Good("old enough").right()
    }

    fun alcCheck(bloodAlc: Int): Either<Bad, Good> {
        println("alcCheck...")
        return if (bloodAlc > 60) Bad("too drunk").left() else Good("sober enough").right()
    }

    suspend fun calculator(age: Int, alc: Int): Either<Bad, Good> = either {
        println("calculating(age=$age, alc=$alc)")
        ageCheck(age).bind() // if any fails => `bind()` will bail out
        alcCheck(alc).bind()
        Good("you may enter")
    }

    runBlocking {
        println("good scenario ....")
        calculator(20, 30)
        println()
        println("bad scenario....")
        calculator(6, 30)
    }
}

// </editor-fold>
// <editor-fold desc="fold and map">

fun `map left and right`() {
    fun maybe(correct: Boolean): Either<Unit, Int> = if (correct) 42.right() else Unit.left()

    val translateLeft: Either<Bad, Int> = maybe(false).mapLeft { Bad("nope") }

    maybe(true).fold(
        // react depending on value
        ifLeft = { println("Left: $it") },
        ifRight = { println("Right: $it") }
    )

    val mapped1: Either<Unit, String> = maybe(true).flatMap { it.toString().right() } // only interested in right
    val mapped2: Either<Unit, String> = maybe(false).flatMap { it.toString().right() } // won't be executed
    println("mapped1=$mapped1, mapped2=$mapped2")
}

// </editor-fold>
// <editor-fold desc="catch">

fun `catch exceptions`() {
    // will catch (non-fatal) Throwables and put in Left
    fun risky(fail: Boolean): Either<Throwable, Int> = Either.catch {
        if (fail) throw Exception("nope")
        42
    }

    risky(false).fold({ println("failed: ${it.message}") }, { println("success: $it") })
}

// </editor-fold>

fun `right() or Either-right()`() {
    /*
    There is a slight difference in both, this is due to how type inference works.

Either.Right is the constructor and it results in Either.Right<A>, whilst right() is a function that results in Either<Nothing, A>.

For example:

listOf(1, 2, 3).fold(Either.Right(0))  { acc, i ->
  acc.map { it + i }
}

This will not compile since fold infers Either.Right<Int> from the initial value, and thus it expects Either.Right<Int> to be returned inside the lambda but we return Either<Nothing, Int>.

This is solved by using:

listOf(1, 2, 3).fold(0.right())  { acc, i ->
  acc.map { it + i }
}

So in general I always use Either.Right, but in an occasional odd case like demonstrated above I use right().

TL;DR right() helps with type inferrence, where using regular constructors break inference.
     */
}

// more advanced shit with EitherEffect: https://gist.github.com/nomisRev/8f10a30f4f087d24a006a0b6093b4c8f