package fpkotlin.ch2_started

fun `functions as first class citizens`() {
    val functionAsVariable = { text: String -> text.lowercase() }

    val orTheLongWay = object: Function1<String, String> {
        override fun invoke(text: String): String =
            text.lowercase()
    }
}

fun `higher order functions = functionals`() {
    fun functionAsParameter(function: (String) -> String) {
    }

    fun functionAsReturn(): (String) -> String {
        return { it }
    }
}

fun `applying first class citizen functions`() {

    val functionAsVariable = { text: String -> text.lowercase() }

    fun <T> transform(item: T, transformer: (T) -> T): T = transformer(item)

    transform("Abc", functionAsVariable) // => "abc"
    transform("Abc", String::uppercase) // => "ABC"
    transform("Abc") { it } // => "Abc"
}