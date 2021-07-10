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
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.slf4jLogger
import java.text.DateFormat

fun main() {
    embeddedServer(Netty, port = 80) {
        main()
    }.start(wait = true)
}

val ktorModule = module {
    single { SimplePonger() } bind Ponger::class // if not bind, then won't be found by Ponger interface - nice :)
}

fun Application.main() {
    install(ContentNegotiation) {
        jackson() {
            enable(SerializationFeature.INDENT_OUTPUT)
            dateFormat = DateFormat.getDateInstance()
        }
    }
    install(Koin) {
        slf4jLogger()
        modules(ktorModule)
    }

    routing {
        installRoutes()
    }
}

fun Routing.installRoutes() {
    val ponger by inject<Ponger>()
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
