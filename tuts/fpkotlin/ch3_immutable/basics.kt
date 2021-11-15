package fpkotlin.ch3_immutable

// kotlin is not pure, it's "dirty", unlike haskell, clojure or F#.

/*
two types of immutability:
1) referential immutability (e.g. MutableList)
2) immutable values (e.g. val i: Int)
*/

/*
modifiers:
var ... mutable reference
val ... immutable reference
const val ... compile time constant (of limited use; e.g. primitive types and string)
*/

// mutable VS immutable collections API (in kotlin we can choose, in many others we can't)

/*
advantages:
- thread safety
    * as there is no mutable state, threads can't interfer with each other, no race conditions
- low coupling
    * coupling also happens via interacting through mutable state, without it, less coupling
- referential transparency:
    * expressions always evaluate to the same value -irrespective of context/any other variance.
    * any expression can be replaced by its evaluated value.
- failure atomicity:
    * failure in one thread/module/part has no effect on other parts, thus can not spread.
    * internal application state will always be consistent, due to decoupling due to immutability.
- compiler optimization
    * instead of the developer having to do it, the compiler can extensively do optimizations (e.g. compile time constants)
- pure functions:
    * two requirements: 1) same result on same arguments 2) no side effects.
    * it's complimentary to immutability.
    * it's basically identical to a mathemtical understanding of a function.
- caching:
    * as a consequence of the points given above, caching is safe to do and improves overall performance
*/
/*
disadvantages:
- bigger memory footprint, especially when working with bigger datasets (need to copy them).
- difficult to wrap head around the mathematical approach.
- not very common still (FP is around for some decades) and all its consequences such as availability of libraries, tooling, documentation, devs...
*/