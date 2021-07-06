package com.github.cpickl.tutorials

import org.gradle.api.artifacts.ModuleDependency
import org.gradle.kotlin.dsl.exclude

@Suppress("MayBeConstant")
object Dependencies {

    object Arrow {
        internal val groupId = "io.arrow-kt"
        private fun arrow(artifactId: String, version: String = Versions.arrow) = "$groupId:$artifactId:$version"
        val core = arrow("arrow-core")
        val optics = arrow("arrow-optics")
        val syntax = arrow("arrow-syntax", Versions.arrowSecondary)
        val fx = arrow("arrow-fx", Versions.arrowSecondary)
        val annotations = arrow("arrow-annotations", Versions.arrowSecondary)
        val fxCoroutines = arrow("arrow-fx-coroutines")

        /** use with kapt(), rather implementation(); requires kapt plugin applied */
        val meta = arrow("arrow-meta")
    }

    val kotlinLogging = "io.github.microutils:kotlin-logging:${Versions.kotlinLogging}"

    object Kotest {
        private fun kotest(artifactId: String, version: String = Versions.kotest) = "io.kotest:$artifactId:$version"
        val junit5 = kotest("kotest-runner-junit5-jvm")
        val assertions = kotest("kotest-assertions-core-jvm")
        val frameworkEngineJvm = kotest("kotest-framework-engine-jvm")

        /** dont' forget to exclude provided arrow */
        val assertionsArrow = kotest("kotest-assertions-arrow-jvm", Versions.kotestArrowAssertions)
    }

    object Ktor {
        private fun ktor(artifactId: String) = "io.ktor:$artifactId:${Versions.ktor}"
        val serverNetty = ktor("ktor-server-netty")

        /** dont' forget to exclude provided arrow */
        val serverTest = ktor("ktor-server-tests")
    }

    val logback = "ch.qos.logback:logback-classic:${Versions.logback}"

    val mockk = "io.mockk:mockk:${Versions.mockk}"

    object Moshi {
        private fun moshi(artifactId: String) = "com.squareup.moshi:$artifactId:${Versions.moshi}"
        val moshi = moshi("moshi")
        val moshiKotlin = moshi("moshi-kotlin")
    }
}

fun ModuleDependency.excludeArrow() {
    exclude(Dependencies.Arrow.groupId)
}
