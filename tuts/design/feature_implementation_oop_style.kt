package design

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() {
    val koin = startKoin {
        modules(module {
            // change to false to disable without any change necessary in the core logic
            single { FeatureFlags(greetEnabled = true) }
            single<Talker> {
                // rewiring beans allow for sophisticated application orchestration, reducing the impact of any changes
                FeatureAwareTalker(PrintTalker, get())
            }
        })
    }.koin

    val talker = koin.get<Talker>()
    talker.say("say message")
    talker.greet("christoph")
}

interface Talker {
    fun greet(name: String)
    fun say(message: String)
}

/** This class is totally unaware of the new feature-enabled switches. Separating concerns. */
object PrintTalker : Talker {
    override fun greet(name: String) {
        println("Hello $name!")
    }
    override fun say(message: String) {
        println(message)
    }
}

/** Main configuration of the new feature-flag feature. */
data class FeatureFlags(
    val greetEnabled: Boolean
)

/** The new business request is implemented in isolation in a separate class. Easy to remove again without touching the underlying logic. */
class FeatureAwareTalker(
    private val delegate: Talker,
    private val features: FeatureFlags
) : Talker by delegate {

    override fun greet(name: String) {
        if (features.greetEnabled) {
            println("Feature Aware: delegating.")
            delegate.greet(name)
        } else {
            println("Feature Aware: skip delegating.")
        }
    }
}
