package tutorial.koin

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestContext
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.koin.core.Koin
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.dsl.single

class ParentService(
    private val sub: SubService
) {
    fun say() = "hello ${sub.number()}"
}

interface SubService {
    fun number(): Int
}

class SubServiceImpl : SubService {
    override fun number() = 1
}

val testModule = module {
    single<ParentService>()
    single { SubServiceImpl() } bind SubService::class
}

class ParentServiceTest : DescribeSpec() { // KoinTest ... great, it's an interface, not a class :)
    init {
        val mockedSub = mockk<SubService>()
        val mockedNumber = 42

        describe("some parent") {
            it("Overwrite sub service via koin module - MANUAL") {
                every { mockedSub.number() } returns mockedNumber
                val koin = koinApplication {
                    modules(
                        testModule,
                        module {
                            single { mockedSub } bind SubService::class
                        }
                    )
                }
                val parent = koin.koin.get<ParentService>()

                parent.say() shouldBe "hello $mockedNumber"

                koin.close()
            }
            it("Overwrite sub service via koin module - IMPLICIT") {
                koined({
                    single { mockedSub } bind SubService::class
                    every { mockedSub.number() } returns mockedNumber
                }) { koin ->
                    val parent = koin.get<ParentService>()

                    parent.say() shouldBe "hello $mockedNumber"
                }
            }
        }
    }

}

class ParentServiceStringTest : StringSpec() {

    init {
        val mockedSub = mockk<SubService>()
        val mockedNumber = 42

        "now with a string" {
            koined({
                single { mockedSub } bind SubService::class
                every { mockedSub.number() } returns mockedNumber
            }) { koin ->
                val parent = koin.get<ParentService>()

                parent.say() shouldBe "hello $mockedNumber"
            }
        }
    }
}

fun TestContext.koined(moduleDeclaration: ModuleDeclaration, testCode: TestCode) {
    val koin = koinApplication { // NO startKoin {} ... avoid global state!
        modules(testModule, module(moduleDeclaration = moduleDeclaration))
    }
    try {
        runBlocking {
            testCode.invoke(this@koined, koin.koin)
        }
    } finally {
        koin.close()
    }
}

typealias TestCode = suspend TestContext.(Koin) -> Unit
