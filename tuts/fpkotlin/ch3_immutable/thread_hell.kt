package fpkotlin.ch3_immutable

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class MutableData(
    var value: Int
)

@DelicateCoroutinesApi
fun main() {
    val data = MutableData(0)

    GlobalScope.launch {
        repeat(10) {
            println("Thread 1: ${data.value++}")
        }
    }
    GlobalScope.launch {
        repeat(10) {
            println("Thread 2: ${data.value++}")
        }
    }
    runBlocking {
        delay(300)
    }
}

/*
Thread 2: 0
Thread 1: 0
Thread 2: 1
Thread 1: 2
Thread 1: 4
Thread 2: 3
Thread 1: 5
Thread 2: 6
Thread 1: 7
Thread 2: 8
Thread 1: 9
Thread 2: 10
Thread 1: 11
Thread 2: 12
Thread 1: 13
Thread 1: 15
Thread 2: 14
Thread 2: 17
Thread 1: 16
Thread 2: 18
*/
