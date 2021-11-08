package fpkotlin.ch3_immutable

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class ImmutableData(
    val value: Int
)

@DelicateCoroutinesApi
fun main() {
    val data = ImmutableData(0)

    fun asyncFunction(threadName: String, data: ImmutableData, times: Int) {
        Thread.sleep(5)
        if(times > 0) {
            println("$threadName: ${data.value}")
            asyncFunction(threadName, data.copy(value = data.value + 1), times - 1)
        }
    }

    GlobalScope.launch {
        asyncFunction("Thread 1", data, 10)
    }
    GlobalScope.launch {
        asyncFunction("Thread 2", data, 10)
    }
    runBlocking {
        delay(1_000)
    }
}
/*
Thread 1: 0
Thread 2: 0
Thread 1: 1
Thread 2: 1
Thread 1: 2
Thread 2: 2
Thread 1: 3
Thread 2: 3
Thread 1: 4
Thread 2: 4
Thread 1: 5
Thread 2: 5
Thread 1: 6
Thread 2: 6
Thread 1: 7
Thread 2: 7
Thread 2: 8
Thread 1: 8
Thread 2: 9
Thread 1: 9
*/