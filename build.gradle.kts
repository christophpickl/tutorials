import com.github.cpickl.tutorials.Dependencies
import com.github.cpickl.tutorials.IntegrationTestTask
import com.github.cpickl.tutorials.excludeArrow

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version com.github.cpickl.tutorials.Versions.Plugins.kotlin
    kotlin("kapt") version com.github.cpickl.tutorials.Versions.Plugins.kotlin
    // ./gradlew kotest ... https://github.com/kotest/kotest-gradle-plugin
    id("io.kotest") version com.github.cpickl.tutorials.Versions.Plugins.kotest
    // $ ./gradlew dependencyUpdates
    id("com.github.ben-manes.versions") version com.github.cpickl.tutorials.Versions.Plugins.versions
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(Dependencies.logback)
    implementation(Dependencies.kotlinLogging)
    implementation(Dependencies.Arrow.core)
    implementation(Dependencies.Arrow.optics)
    implementation(Dependencies.Arrow.syntax)
    implementation(Dependencies.Arrow.fx)
    implementation(Dependencies.Arrow.annotations)
    implementation(Dependencies.Ktor.serverNetty)
    implementation(Dependencies.Ktor.jackson)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.ktor)
    implementation(Dependencies.Koin.loggerSlf4j)
    kapt(Dependencies.Arrow.meta)

    testImplementation(Dependencies.Ktor.serverTest) { excludeArrow() }
    testImplementation(Dependencies.Kotest.frameworkEngineJvm)
    testImplementation(Dependencies.Kotest.junit5)
    testImplementation(Dependencies.Kotest.assertions)
    testImplementation(Dependencies.Kotest.assertionsArrow)
    testImplementation(Dependencies.Koin.test)
    testImplementation(Dependencies.mockk)
}

kotlin.sourceSets["test"].kotlin.srcDirs("tuts")

tasks.withType<Test> {
    useJUnitPlatform()
}
//val test by tasks.getting(Test::class) {
//    useJUnitPlatform { }
//}

// ./gradlew test -Ptests=all
tasks.withType<io.kotest.gradle.Kotest> {
    if (findProperty("tests") == "all") {
        println("Running all tests.")
    } else {
        println("Running only unit tests. Run `./gradlew test -Ptests=all` to also run integration tests.")
        setTags("!Integration")
    }

}

// ./gradlew -q itest
tasks.register<IntegrationTestTask>("itest") {
    tagExpression.set("Unit & Database & !Linux")
}

//the<IntegrationTestTask>().tagExpression.set("Unit & Database & !Linux")
