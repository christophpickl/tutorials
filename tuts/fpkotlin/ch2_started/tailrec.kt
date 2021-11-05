package fpkotlin.ch2_started

import kotlin.system.measureNanoTime

fun naiveFactorial(value: Long): Long {
    var result = 1L
    for(i in 1..value) {
        result *= i
    }
    return result
}

fun recFactorial(value: Long): Long {
    fun go(value: Long, accumulator: Long): Long =
        if(value <= 0) accumulator else go(value - 1, value * accumulator)

    return go(value, 1)
}

fun tailrecFactorial(value: Long): Long {
    tailrec fun go(value: Long, accumulator: Long): Long =
        if(value <= 0) accumulator else go(value - 1, value * accumulator)

    return go(value, 1)
}

fun main() {
    val value = 10_000L
    val measure = ::measureNanoTime

    val timeNaive = measure {
        naiveFactorial(value)
    }
    val timeRec = measure {
        recFactorial(value)
    }
    val timeTailrec = measure {
        tailrecFactorial(value)
    }

    println("Naive:     $timeNaive")   //  536541
    println("Recursive: $timeRec")     // 1085587
    println("Tailrec:   $timeTailrec") //  217778
}
