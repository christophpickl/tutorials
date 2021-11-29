package fpkotlin.ch5_functions2

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
