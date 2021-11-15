package com.github.cpickl.tutorials

import org.gradle.api.artifacts.ModuleDependency
import org.gradle.kotlin.dsl.exclude

@Suppress("MayBeConstant")
object Dependencies {

    object Arrow {
        internal val groupId = "io.arrow-kt"
        private fun artifact(artifactId: String, version: String = Versions.arrow) = "$groupId:arrow-$artifactId:$version"
        val core = artifact("core")
        val optics = artifact("optics")
        val syntax = artifact("syntax", Versions.arrow2)
        val fx = artifact("fx")
        val annotations = artifact("annotations")
        val fxCoroutines = artifact("fx-coroutines")
        /** use with kapt(), rather implementation(); requires kapt plugin applied */
        val meta = artifact("meta")
    }

    object Koin {
        private fun artifact(artifactIdSuffix: String) = "io.insert-koin:koin-$artifactIdSuffix:${Versions.koin}"
        val core = artifact("core")
        val ktor = artifact("ktor")
        val loggerSlf4j = artifact("logger-slf4j")
        val test = artifact("test")
    }

    val kotlinLogging = "io.github.microutils:kotlin-logging:${Versions.kotlinLogging}"

    object Kotest {
        private fun artifact(artifactId: String, version: String = Versions.kotest) = "io.kotest:$artifactId:$version"
        val junit5 = artifact("kotest-runner-junit5-jvm")
        val assertions = artifact("kotest-assertions-core-jvm")
        val frameworkEngineJvm = artifact("kotest-framework-engine-jvm")

        /** dont' forget to exclude provided arrow */
        val assertionsArrow = artifact("kotest-assertions-arrow-jvm", Versions.kotestArrowAssertions)
    }

    object Ktor {
        private fun artifact(artifactIdSuffix: String) = "io.ktor:ktor-$artifactIdSuffix:${Versions.ktor}"
        val serverNetty = artifact("server-netty")
        val jackson = artifact("jackson")

        /** dont' forget to exclude provided arrow */
        val serverTest = artifact("server-tests")
    }

    val logback = "ch.qos.logback:logback-classic:${Versions.logback}"

    val mockk = "io.mockk:mockk:${Versions.mockk}"

    object Moshi {
        private fun artifact(artifactId: String) = "com.squareup.moshi:$artifactId:${Versions.moshi}"
        val moshi = artifact("moshi")
        val moshiKotlin = artifact("moshi-kotlin")
    }
}

fun ModuleDependency.excludeArrow() {
    exclude(Dependencies.Arrow.groupId)
}
