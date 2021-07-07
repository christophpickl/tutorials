package tutorial.kotest

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.AutoScan
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase

class Full1Test : StringSpec() {
    init {
        listener(PrintAllTestListener())
        "test method 1/1" {
            println("executing test method 1/1")
        }
        "test method 1/2" {
            println("executing test method 1/2")
        }
    }
}

class Full2Test : StringSpec() {
    init {
        listener(PrintAllTestListener())
        "test method 2/1" {
            println("executing test method 2/1")
        }
        "test method 2/2" {
            println("executing test method 2/2")
        }
    }
}

@AutoScan // to automatically be picked up by kotest
object MyProjectListener : ProjectListener {
    override suspend fun beforeProject() {
        println("MyProjectListener.beforeProject")
    }

    override suspend fun afterProject() {
        println("MyProjectListener.afterProject")
    }
}

class PrintAllTestListener : TestListener {
    override suspend fun beforeAny(testCase: TestCase) {
        println("beforeAny")
    }

    override suspend fun beforeContainer(testCase: TestCase) {
        println("beforeContainer")
    }

    override suspend fun beforeInvocation(testCase: TestCase, iteration: Int) {
        println("beforeInvocation")
    }

    override suspend fun beforeSpec(spec: Spec) {
        println("beforeSpec")
    }

    override suspend fun beforeTest(testCase: TestCase) {
        println("beforeTest")
    }

    override suspend fun beforeEach(testCase: TestCase) {
        println("beforeEach")
    }
}
