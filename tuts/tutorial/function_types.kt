package tutorial

import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun doSomething(asyncExecutor: (() -> Unit) -> Unit) {
    asyncExecutor {
        println("this is async.")
    }
}

@DelicateCoroutinesApi
fun runAsync(block: () -> Unit) {
    println("going to run async.")
    GlobalScope.launch {
        block()
    }
}

@DelicateCoroutinesApi
fun main() {
    doSomething(::runAsync)
}

class AsyncTest : StringSpec() {
    init {
        "foo" {
            println("before test")
            doSomething(::testSync)
            println("after test")
        }
    }
}

fun testSync(block: () -> Unit) {
    println("running sync for tests.")
}