package fpkotlin.ch2_started

typealias Processor<T> = (T) -> Unit

fun <T> withProcessor(item: T, processor: Processor<T>) {
    processor(item)
}

// using a function type alias like an (functional) interface
class PrintProcessor<T> : Processor<T> {
    override fun invoke(t: T) {
        println(t)
    }
}

fun main() {
    withProcessor("x", PrintProcessor())
    withProcessor("x", ::println) // same as above ;)
}