package tutorial.kotest

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.AfterEach
import io.kotest.core.spec.AutoScan
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase

// https://kotest.io/docs/framework/listeners.html
/**
 * The compound interface [io.kotest.core.listeners.TestListener] acts as the main hook.
 * Additionally there is the [io.kotest.core.listeners.ProjectListener] interface.
 */
// before/after
// - container
// - each
// - any
// - test
// - invocation
// - spec (prepare/finalize)


val sharedAfterEach: AfterEach = {
    println("sharedBefore: ${it.a.displayName} => ${it.b.status}")
}

class SimpleHookTest : StringSpec({
    beforeEach {
        println("beforeEach")
    }
    beforeTest {
        println("beforeTest")
    }
    afterEach(sharedAfterEach)
    "test 1" {
        println("test 1")
    }
})

class ReuseHookTest : StringSpec({
    // ORDER is of relevance!
    listener(MyTestListener) // also see, for example: NoSystemOutListener

    beforeEach {
        println("Test.beforeEach: ${it.displayName}")
    }


    "test 1" {
        println("test 1")
    }
})

@AutoScan // to automatically be picked up by kotest
object MyProjectListener : ProjectListener {
    override suspend fun beforeProject() {
        println("MyProjectListener.beforeProject")
    }
    override suspend fun afterProject() {
        println("MyProjectListener.afterProject")
    }
}

object MyTestListener : TestListener {
    override suspend fun beforeEach(testCase: TestCase) {
        println("MyTestListener.beforeEach: ${testCase.displayName}")
    }
}