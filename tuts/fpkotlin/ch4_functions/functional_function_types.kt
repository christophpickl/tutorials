package fpkotlin.ch4_functions

import arrow.core.curried
import arrow.core.partially3
import arrow.core.uncurried

// lambda functions as property
// high-order functions ... function that A) takes another function as an argument B) returns another function
// pure functions ...  1. for same arguments return same result (result fully/only depends on args) 2. has no (in/direct) side effects; basically are "mathematical functions"
// partial functions ... fix certain (not all) arguments of a function, and return a new function

/*
terminology

function ... named statement(s) with parameters/return value
method ... instance bound function
action ... has side effects
procedure ... ?
subroutine ... ?

these here differ in bindings to environment/context (or just synonyms?)
lambda (expression) ... anonymous function
closure ... created by lambdas (cpatures context)
(code-)block ... (originally in smalltalk)
*/

fun `function as property`() {
    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
    val sum2 = { x: Int, y: Int -> x + y }
    // val sum3 = { x, y -> x + y } ... nope, parameters need to have types declared
}

fun `high-order functions`() {
    fun receivingFunction(string: String, transformer: (String) -> String) = transformer(string)
    fun returningFunction(string: String) = { it: String -> "$string - $it"}

    receivingFunction("x", returningFunction("y")) // => "y - x"
}

fun `partial function`() {
    // poor man's example:
    fun fun3(x1: Int, x2: Int, x3: Int) = x1 + x2 + x3
    fun fun2(x1: Int, x2: Int) = fun3(x1, x2, 10)
    fun2(1, 2)

    // using arrow:
    val fun2b: (x1: Int, x2: Int) -> Int = ::fun3.partially3/*set the third argument*/(10)
    fun2b(1, 2)

    val curried: (x1: Int) -> (x2: Int) -> Int = ::fun2.curried()
    curried(1)(2) // 1 + 2 + 10 => 13

    fun yetCurried(x1: Int): (Int) -> Int = {x2: Int -> x1 + x2 }
    ::yetCurried.uncurried()(12, 30) // => 42
}