package fpkotlin.ch2_started

fun main() {
//    val numbers = Cons(1, Cons(2, Cons(3, Nil)))
    val numbers = intListOf(1, 2, 3)
    println(numbers)
    numbers.forEach(::println)
    println(numbers.joinToString()) // TODO 1, 22, 3
    println(numbers.fold("") { acc, it -> "$acc$it" }) // => "123"
    println(numbers.map { it * 2 }) // 2, 4, 6
}

fun intListOf(vararg numbers: Int): FunList<Int> =
    if (numbers.isEmpty()) Nil
    else Cons(numbers.first(), intListOf(*numbers.drop(1).toTypedArray().toIntArray()))

sealed class FunList<out ItemType> {

    fun forEach(itemProcessor: (ItemType) -> Unit) {
        tailrec fun forEachRec(list: FunList<ItemType>, itemProcessor: (ItemType) -> Unit) {
            when (list) {
                is Cons -> {
                    itemProcessor(list.head)
                    forEachRec(list.tail, itemProcessor)
                }
                Nil -> Unit
            }
        }
        forEachRec(this, itemProcessor)
    }

    fun <NewType> fold(init: NewType, accumulator: (NewType, ItemType) -> NewType): NewType {
        tailrec fun foldRec(list: FunList<ItemType>, init: NewType, accumulator: (NewType, ItemType) -> NewType): NewType =
            when (list) {
                is Cons -> foldRec(list.tail, accumulator(init, list.head), accumulator)
                is Nil -> init
            }
        return foldRec(this, init, accumulator)
    }

    fun <NewType> foldRight(init: NewType, accumulator: (NewType, ItemType) -> NewType): NewType =
        reverse().fold(init, accumulator)

    // TODO compile error regarding out type parameter?!
//    fun reduce(accumulator: (ItemType, ItemType) -> ItemType): ItemType =
//        when (this) {
//            is Nil -> throw Exception("Must not be empty!")
//            is Cons -> fold(head, accumulator)
//    }

    fun <NewType> map(mapper: (ItemType) -> NewType): FunList<NewType> {
        // TODO how to make tail recursive?
//        fun mapRec(list: FunList<ItemType>, mapper: (ItemType) -> NewType): FunList<NewType> =
//            when (list) {
//                is Cons -> Cons(mapper(list.head), mapRec(list.tail, mapper))
//                is Nil -> Nil
//            }
//        return mapRec(this, mapper)
        val init: FunList<NewType> = Nil
        return foldRight(init) { tail, head -> Cons(mapper(head), tail)}
    }

    fun <NewType> combine(empty: NewType, combiner: (NewType, ItemType, ItemType) -> NewType): NewType {
        tailrec fun combineRec(list: FunList<ItemType>, empty: NewType, combined: NewType, combiner: (NewType, ItemType, ItemType) -> NewType): NewType =
            when (list) {
                // TODO here is the bug somewhere
                is Cons -> when (list.tail) {
                    is Cons -> combineRec(list.tail, empty, combiner(combined, list.head, list.tail.head), combiner)
                    is Nil -> combined
                }
                is Nil -> empty
            }
        return combineRec(this, empty, empty, combiner)
    }

    fun reverse(): FunList<ItemType> {
        val init: FunList<ItemType> = Nil
        return fold(init) { acc, i -> Cons(i, acc)}
    }

    fun joinToString(): String =
        combine("") { acc, x1, x2 -> "$acc$x1, $x2" }
}

object Nil : FunList<Nothing>() {
    override fun toString() = "Nil"
}

data class Cons<out T>(
    val head: T,
    val tail: FunList<T>
) : FunList<T>()

