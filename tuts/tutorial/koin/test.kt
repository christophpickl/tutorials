package tutorial.koin

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.scopes.StringSpecScope
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.test.KoinTest
import org.koin.test.inject

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

class ParentServiceTest : StringSpec(), KoinTest { // great, it's an interface, not a class :)

    private val parent : ParentService by inject()

    init {
        val mockedSub = mockk<SubService>()
        val mockedNumber = 42

        "Overwrite sub service via koin module - MANUAL" {
            every { mockedSub.number() } returns mockedNumber
            startKoin {
                modules(
                    testModule,
                    module {
                        single { mockedSub } bind SubService::class
                    }
                )
            }

            parent.say() shouldBe "hello $mockedNumber"
        }

        "Overwrite sub service via koin module - IMPLICIT".koined({
            single { mockedSub } bind SubService::class
        }) {
            every { mockedSub.number() } returns mockedNumber

            parent.say() shouldBe "hello $mockedNumber"
        }
    }

    private fun String.koined(moduleDeclaration: ModuleDeclaration, testCode: TestCode) {
        startKoin {
            modules(testModule, module(moduleDeclaration = moduleDeclaration))
        }
        try {
            this.invoke(testCode)
        } finally {
            stopKoin()
        }
    }
}

typealias TestCode = suspend StringSpecScope.() -> Unit
