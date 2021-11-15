package fpkotlin.ch4_functions

fun `single-expression functions`() {
    fun `nested function`(a: Int, b: Int) = a + b
}

fun `extension functions`() {
    fun String.extendedFunctionality() {}
    "".extendedFunctionality()
}

inline fun inlinedIf(condition: Boolean, code: () -> Unit) {
    // works best with parameters of functional types
    if (condition) {
        code()
    }
}

fun `inline functions`() {
    inlinedIf(true) {
        println("yes")
    }
    // same as:
    if(true) {
        println("yes")
    }
}

fun `infix notation`() {
    // infix must be extension or member type
    infix fun Int.`+`(b: Int) = this + b

    val result = 3 `+` 3
}

fun `operator overloading`() {
    class Foo(val value: Int) {
        operator fun plus(other: Foo) = Foo(this.value + other.value)
    }
    val f1 = Foo(1)
    val f2 = Foo(2)
    val f3 = f1 + f2
}
