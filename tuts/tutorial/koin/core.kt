package tutorial.koin

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.environmentProperties
import org.koin.java.KoinJavaComponent.inject
import org.koin.logger.SLF4JLogger

// https://insert-koin.io/
// https://insert-koin.io/docs/reference/introduction
class SomeService(
    private val repo: Repository,
    private val config: PropertyProvider,
    ) {
    init {
        println("new SomeService(repo=$repo)")
    }
    fun greetAll() {
        println("Before greeting, this is the property: [${config.getTheProperty()}]")
        repo.loadAllNames().forEach {
            println("Hello $it!")
        }
    }
}

interface Repository {
    fun loadAllNames(): List<String>
}

class InMemoryRepository: Repository {
    init {
        println("new InMemoryRepository")
    }
    override fun loadAllNames() = listOf("Christoph", "World")
}

class PropertyProvider(
    private val propertyValue: String
) {
    fun getTheProperty() = propertyValue
}

val myModule = module(createdAtStart = true) {
    single<SomeService>() // single(createdAtStart = true) { SomeService(get()) }
    single() { PropertyProvider(getProperty("propertyKey", "defaultPropertyValue")) }
//    single<SomeService>(named("foo"))
    single<InMemoryRepository>() bind Repository::class // TODO i want injecting InMemoryRepository impossible here
//    factory<Repository>(qualifier = StringQualifier("memory")) bind InMemoryRepository::class
    // bind(), binds()
    // scope{}, scoped{}
}

fun main() {
    val koin: Koin = koinApplication {
//    val koin = startKoin { // same as koinApplication {} + register it into koin's GlobalContext
//        logger(PrintLogger(level = Level.DEBUG))
        allowOverride(false)
        logger(SLF4JLogger(level = Level.DEBUG))
        properties(mapOf("propertyKey" to "specific"))
//        fileProperties("fileName.properties")
        environmentProperties() // load properties from OS environment into Koin container
        modules(myModule)
    }.koin
    val service = koin.get<SomeService>()
    service.greetAll()
}
