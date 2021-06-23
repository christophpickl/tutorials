repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version Versions.Plugins.kotlin
    kotlin("kapt") version Versions.Plugins.kotlin
    // $ ./gradlew dependencyUpdates
    id("com.github.ben-manes.versions") version Versions.Plugins.versions
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(Dependencies.logback)
    implementation(Dependencies.kotlinLogging)
    implementation(Dependencies.Arrow.core)
    implementation(Dependencies.Arrow.optics)
    implementation(Dependencies.Arrow.syntax)
    kapt(Dependencies.Arrow.meta)

    testImplementation(Dependencies.Kotest.junit5)
    testImplementation(Dependencies.Kotest.assertions)
    testImplementation(Dependencies.Kotest.assertionsArrow)
    testImplementation(Dependencies.mockk)
}

kotlin.sourceSets["test"].kotlin.srcDirs("tuts")

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}
