package fpkotlin.ch2_started

fun main() {
    `collection samples`()
}

fun `collection samples`() {
    val numbers = listOf(1, 2, 3)

    numbers.map { it * 2 } // => 2, 4, 6
    numbers.filter { it < 2 } // => 1
    numbers.fold("") { acc, it -> "$acc$it" } // => "123"
    numbers.foldRight("") { acc, it -> "$acc$it"} // => "123", but starting from last, going to previous/left
    numbers.fold(0) { acc, it -> acc + it } // => 6
    numbers.sum() // same as above ;)
    numbers.reduce { acc, it -> acc + it } // same as fold, but initial is set to first element
    // reduceRight for other direction
}