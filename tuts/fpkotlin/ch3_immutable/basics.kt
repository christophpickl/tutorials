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
    * TODO
- low coupling
    * TODO
- referential transparency:
    * expressions always evaluate to the same value -irrespective of context/any other variance.
    * any expression can be replaced by its evaluated value.
- failure atomicity:
    * Failure in one thread/module/part has no effect on other parts, thus can not spread.
    * Internal application state will always be consistent, due to decoupling due to immutability.
- compiler optimization
    * TODO
- pure functions:
    * TODO
*/
