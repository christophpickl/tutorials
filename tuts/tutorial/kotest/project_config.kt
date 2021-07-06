package tutorial.kotest

import io.kotest.common.ExperimentalKotest
import io.kotest.core.config.AbstractProjectConfig

// https://kotest.io/docs/framework/project-config.html

object MyProjectConfig : AbstractProjectConfig() {

    @ExperimentalKotest
    override val concurrentTests = 3
    override val failOnIgnoredTests = true

    override fun beforeAll() {
        println("MyProjectConfig.beforeAll")
    }
    override fun afterAll() {
        println("MyProjectConfig.afterAll")
    }

    override fun listeners() = listOf(MyTestListener)

}