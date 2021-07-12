package tutorial.koin

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.logger.slf4jLogger
import java.text.DateFormat

fun main() {
    embeddedServer(Netty, port = 80) {
        applicationSetup()
    }.start(wait = true)
}

private fun Application.applicationSetup() {
    install(ContentNegotiation) {
        jackson() {
            enable(SerializationFeature.INDENT_OUTPUT)
            dateFormat = DateFormat.getDateInstance()
        }
    }

    lateinit var koin: Koin
    install(org.koin.ktor.ext.Koin) {
        koinSetup()
        koin = this.koin
    }

    routing {
        installRoutes(koin)
    }
}

private val koinModule = module {
    single { SimplePonger() } bind Ponger::class // if not bind, then won't be found by Ponger interface - nice :)
}

private fun KoinApplication.koinSetup() {
    slf4jLogger()
    modules(koinModule)
}

private fun Routing.installRoutes(koin: Koin) {
    val ponger = koin.get<Ponger>()
    get("/ping") {
        call.respond(ponger.say())
    }
}

interface Ponger {
    fun say(): String
}

class SimplePonger : Ponger {
    override fun say() = "pong"
}
