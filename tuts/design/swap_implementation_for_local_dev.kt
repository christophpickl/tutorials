package design

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

/*
PROD main running ...
Service#execute()
SubServiceImpl#subExecute()

DEV/TEST main running ...
Service#execute()
TestableSubService#subExecute()
*/

fun main() {
    println("PROD main running ...")
    productionMain()
    stopKoin()
    println()

    println("DEV/TEST main running ...")
    developmentMain()
}

// src/main/kotlin ==========================================================================================

val mainModule = module {
    single { Service(get()) }
    single<SubService> { SubServiceImpl }
}

fun productionMain() {
    runApplication(startKoin {
        modules(mainModule)
    }.koin)
}

fun runApplication(koin: Koin) {
    val service = koin.get<Service>()
    service.execute()
}

class Service(
    private val subService: SubService
) {
    fun execute() {
        println("Service#execute()")
        subService.subExecute()
    }
}

interface SubService {
    fun subExecute()
}

object SubServiceImpl : SubService {
    override fun subExecute() {
        println("SubServiceImpl#subExecute()")
    }
}

// src/test/kotlin ==========================================================================================

val testModule = module {
    single<SubService> { TestableSubService }
}

fun developmentMain() {
    runApplication(startKoin {
        modules(mainModule, testModule)
    }.koin)
}

object TestableSubService : SubService {
    override fun subExecute() {
        println("TestableSubService#subExecute()")
    }
}
