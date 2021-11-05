package fpkotlin.ch2_started

fun unless(condition: Boolean, code: () -> Unit) {
    if (!condition) {
        code()
    }
}

fun `my own dsl`() {
    unless(1 + 1 == 3) {
        // opposite of if ;)
    }
}
