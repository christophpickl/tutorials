package com.github.cpickl.tutorials

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.testing.Test

//abstract class IntegrationTestTask : DefaultTask() {
abstract class IntegrationTestTask : Test() { // see: https://github.com/kotest/kotest-gradle-plugin/blob/master/src/main/kotlin/io/kotest/gradle/Kotest.kt

    @get:Input
    abstract val tagExpression: Property<String>

    init {
        tagExpression.convention("hello from GreetingTask")
        group = "verification"
    }

    @TaskAction
    fun itest() {
        println("IntegrationTestTask running: ${tagExpression.get()}")
    }
}
