package tutorial.koin

import org.koin.dsl.koinApplication
import org.koin.dsl.module

private class Foo(
    val bar: String
)

fun main() {
    val koin = koinApplication {
        modules(listOf(
            module(createdAtStart = true) {
                single { Foo("original") }
            },
            module(createdAtStart = true) {
                single { Foo("override") }
            }
        ))
    }.koin
    koin.createEagerInstances()
    println("Bar: ${koin.get<Foo>().bar}")
}