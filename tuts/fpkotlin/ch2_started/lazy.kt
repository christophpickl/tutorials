package fpkotlin.ch2_started

fun `using delegate properties`() {
    val lazyValue by lazy {
        println("2: lazy evaluation")
        42
    }
    println("1: before using lazy value")
    println(lazyValue) // 3: 42
}

fun `short circuit evaluation can help`() {
    if (true || (0 / 0 < 10)) {
        // right hand side will not be evaluated
    }
    if (false && (0 / 0 < 10)) {
        // right hand side will not be evaluated
    }
}

fun `lazy list`() {
    listOf(2 + 1, 3 / 0).size // will FAIL!
    listOf({ 2 + 1 }, { 3 / 0 }).size // => 2
}