package fpkotlin.ch5_functions2

import arrow.core.Either
import arrow.core.right
import tutorial.kotest.T

/*
- extension functions
- operator overloading
- type-safe builders
- inline functions
- recursion and co-recursion
*/

fun `single expression function`() = 42
fun `dont omit return type by using type inference if not intuitive`() = someOtherFunc()
fun someOtherFunc() = "maybe"


fun `pass me variable amount of arguments`(vararg arguments: Any) {
    arguments.forEach(::println)
}
// fun `multi varargs invalid`(vararg arg1: Any, vararg arg2: Any) ... how should the compiler know where one starts and other ends?!
// fun `also invalid`(vararg arg1: Int, vararg arg2: String) ... strange, but not supported by kotlin

fun `vararg usage`() {
    `pass me variable amount of arguments`()
    `pass me variable amount of arguments`(1)
    `pass me variable amount of arguments`(1, 2, 3)
}

fun <IN, OUT> transform(vararg ints: IN, f: (IN) -> OUT): List<OUT> = ints.map(f)

fun `transform usage`() {
    transform(1, 2, 3) {
        it * 2
    } // == 2, 4, 6
}

fun <T> emit(item: T, vararg listeners: (T) -> Unit) = listeners.forEach { it(item) }
fun `emit usage showing demo of vararg lambdas`() {
    // emit("") { "hello $it" } ... needs to be inside paranthesis (vararg restriction!)
    emit("foo", ::println, { println("hello $it") })
}

fun `named arguments`(before: Int, vararg xs: String, after: Int) {

}
fun `named arguments usage`() {
    `named arguments`(1, "a", "b", after = 2) // use named arg here otherwise impossible!
}

fun `named lambda arguments`(f: (i: Int, s: String) -> Unit) { // for documentation purpose only
    // f(i = 1, s = "a") ... not allowed!
    f(1, "a")
}

// ================================================================================================

class Super {
    companion object
    object Sub
}

fun Super.Companion.foo() {}
fun Super.Sub.bar() {}

infix fun Int.`+++`(other: Int) = this + (other * 3)
fun usage() {
    2 `+++` 3 // = 2 + (3 * 3) == 11
}

// chaining infix functions :)

object All {
    infix fun your(base: Pair<Base, Us>) {}
}
object Base {
    infix fun are(belong: Belong) = this
}
object Belong
object Us
fun usageInfix() {
    All your (Base are Belong to Us)
}

// ================================================================================================
// operators

class Op {
    companion object {
        operator fun invoke(pseudoSecondaryConstructor: Any): Either<Error, Op> {
            // in case you need some validation (could make the real constructor private ;)
            return Op().right()
        }
    }
    operator fun invoke() {}
    operator fun get(indexAccess: String) = "looked up value"
    operator fun set(indexAccess: String, value: String) {}
    operator fun get(indexAccess: String, other: Int) = "looked up value 2"
    operator fun set(indexAccess: String, other: Int, value: String) {}

    fun demo() {
        val op = Op()
        op() // directly invoke (without specifying name)

        val func = object : Function0<Any> {
            override fun invoke(): Any = 1
        }
        func() // same for functional interfaces (like SAMs)

        op["key"] // operator get
        op["write"] = "value" // operator set
        op["key", 42] // multi-arg index access
        op["key", 42] = "value" // multi-arg set
    }
}

// Op() + Op()
operator fun Op.plus(other: Op) = Op()
// Op() - Op()
operator fun Op.minus(other: Op) = Op()
// Op() * Op()
operator fun Op.times(other: Op) = Op()
// Op() / Op()
operator fun Op.div(other: Op) = Op()
// Op() % Op()
operator fun Op.rem(other: Op) = Op()
// Op()..Op()
operator fun Op.rangeTo(other: Op) = Op()
// Op() in Op()
operator fun Op.contains(other: Op) = true
// Op() += Op()
operator fun Op.plusAssign(other: Op) {}
// Op() -= Op()
operator fun Op.minusAssign(other: Op) {}
// Op() *= Op()
operator fun Op.timesAssign(other: Op) {}
// Op() /= Op()
operator fun Op.divAssign(other: Op) {}
// Op() %= Op()
operator fun Op.remAssign(other: Op) {}
// Op() < Op()
operator fun Op.compareTo(other: Op) = 0

// !Op()
operator fun Op.not() = 1
// +Op()
operator fun Op.unaryPlus() = 1
// -Op()
operator fun Op.unaryMinus() = 1
// ++Op() or Op()++
operator fun Op.inc() = Op()
// --Op() or Op()--
operator fun Op.dec() = Op()
