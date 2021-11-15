package fpkotlin.ch4_functions

fun `destructuring sample`() {
    fun withPair(): Pair<Int, String> = 1 to "one"
    val (number: Int, label: String) = withPair()

    data class GeneratedDestructuring(
        val x: Int,
        val y: Int,
        val z: Int
    )
    val (x, y, z) = GeneratedDestructuring(1, 2, 3)
}

// default arguments
// named arguments