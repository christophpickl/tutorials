package tutorial.arrow.selfmade

import io.kotest.core.spec.style.StringSpec

sealed class Option<ElementType> {
    fun <NewType> map(transformer: (ElementType) -> NewType): Option<NewType> = when (this) {
        is None -> None()
        is Some -> Some(transformer(element))
    }
}

data class Some<ElementType>(val element: ElementType) : Option<ElementType>() {
    override fun toString() = "Some[$element]"
}

class None<ElementType> : Option<ElementType>() {
    override fun toString() = "None[]"
}

fun <T> Iterable<T>.firstOrOption() = firstOrNull()?.let { Some(it) } ?: None()

class OptionTest : StringSpec() {
    init {
        "map" {
            val optionalValues = listOf(Some(42), None())
            optionalValues.forEach { opt ->
                println("Option: $opt")
                println("Option * 2: ${opt.map { it * 2 }}")
            }
        }
        "firstOrOption" {
            val iterables = listOf(listOf("someValue"), emptyList())

            iterables.forEach { iterable ->
                println("First of ($iterable): ${iterable.firstOrOption()}")
            }
        }
    }

    fun giveSome(): Option<Int> = Some(42)
    fun giveNone(): Option<Int> = None()
}
