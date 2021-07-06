package tutorial.kotest

import io.kotest.core.NamedTag
import io.kotest.core.Tag
import io.kotest.core.spec.style.StringSpec

// gradle test -Dkotest.tags="Linux & !Database"
// available "&" (and), "|" (or), "!" (not)

// @Tags(Tag.Linux)
class TagsTest : StringSpec() {
    init {
        tags(T.Integration)
        "db test".config(tags = setOf(T.Database)) {
            println("db test")
        }
        "linux test".config(tags = setOf(T.Linux)) {
            println("linux test")
        }
        "db & linux test".config(tags = setOf(T.Database, T.Linux)) {
            println("db & linux test")
        }
    }
}

//object DatabaseTaggedTestsOnlyConfig : AbstractProjectConfig() {
//    override fun beforeAll() {
//        println("DatabaseTaggedTestsOnlyConfig.beforeAll() ... limiting to Database tags")
//        RuntimeTagExpressionExtension.expression = T.Database.name
//    }
//}

object T {
    val Unit = NamedTag("Unit")
    val Database = NamedTag("Database")
    val Integration = NamedTag("Integration")
    val Linux = NamedTag("Linux")
    object DatabaseX : Tag()


}
