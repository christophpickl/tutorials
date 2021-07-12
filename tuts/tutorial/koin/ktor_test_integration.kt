package tutorial.koin

import com.fasterxml.jackson.databind.SerializationFeature
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.mockk.every
import io.mockk.mockk
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.ext.modules
import org.koin.logger.slf4jLogger
import java.text.DateFormat

val koinProdSetup: KoinApplication.() -> Unit = {
    slf4jLogger()
    modules(koinModule)
}

private fun Application.ktorProdSetup(koinSetup: KoinApplication.() -> Unit = koinProdSetup) {
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

private fun Routing.installRoutes(koin: Koin) {
    val controller = koin.get<Controller>()
    get("/say") {
        call.respond(controller.say())
    }
}

interface Controller {
    fun say(): String
}

class RealController : Controller {
    override fun say() = "real"
}


private val koinModule = module {
    single { RealController() } bind Controller::class // if not bind, then won't be found by Ponger interface - nice :)
}

class KtorKoinTest : DescribeSpec() {
    init {
        describe("ktor and koin") {
            lateinit var mockController: Controller
            beforeTest {
                mockController = mockk()
            }
            it("mocked controller") {
                withServiceTestApplication({
                    single { mockController } bind Controller::class
                    every { mockController.say() } returns "mocked"
                }) {
                    val response = handleRequest(HttpMethod.Get, "/say").response

                    response.content shouldBe "mocked"
                }
            }
        }
    }
}

private fun withServiceTestApplication(testModule: ModuleDeclaration = {}, test: TestApplicationEngine.() -> Unit) {
    withTestApplication({
        ktorProdSetup {
            koinProdSetup()
            modules(module(createdAtStart = true, testModule))
        }
    }, test)
}
