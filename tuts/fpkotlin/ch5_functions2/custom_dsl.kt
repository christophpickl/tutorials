package fpkotlin.ch5_functions2

// DSL = domain-specific language (e.g. HTML, SQL)
// GPL = general-purpose language (e.g. Kotlin)
// interal DSL = DSL running in GPL

// type-safe builders with DSLs:
fun main() {
    val htmlCode = html {
        head {
            title = "My Title"
        }
        body {
            h1 {
                +"asdf"
            }
            // head {  } ... impossible thanks to the @DslMarker => restrict implicit receiver access :)
        }
    }
    println(htmlCode)
    // <html><head><title>My Title</title></head><body><h1>asdf</h1></body></html>
}

fun html(code: HtmlCode.() -> Unit): String =
    StringBuilder().let {
        HtmlCode().apply(code).render(it)
        it.toString()
    }

interface Renderable {
    fun render(stringBuilder: StringBuilder)
}

private fun List<Renderable>.render() = StringBuilder().let { stringBuilder ->
    forEach { renderable ->
        renderable.render(stringBuilder)
    }
    stringBuilder.toString()
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class HtmlDsl

private infix fun Tag.wrap(content: String) = "<$tagName>$content</$tagName>"

enum class Tag(val tagName: String) {
    Html("html"),
    Head("head"),
    Title("title"),
    Body("body"),
    H1("h1"),
}

private operator fun StringBuilder.plusAssign(string: String) {
    append(string)
}

@HtmlDsl
class HtmlCode : Renderable {
    private var head = HeadCode()
    private var body = BodyCode()

    fun head(code: HeadCode.() -> Unit) {
        head = HeadCode().apply(code)
    }

    fun body(code: BodyCode.() -> Unit) {
        body = BodyCode().apply(code)
    }

    override fun render(stringBuilder: StringBuilder) {
        stringBuilder += Tag.Html wrap listOf(head, body).render()
    }
}

@HtmlDsl
class HeadCode : Renderable {
    private var _title: String? = null
    var title: String
        get() = _title ?: ""
        set(value) {
            _title = value.ifEmpty { null }
        }

    override fun render(stringBuilder: StringBuilder) {
        stringBuilder += Tag.Head wrap (_title?.let { Tag.Title wrap it } ?: "")
    }
}

@HtmlDsl
class BodyCode : Renderable {
    private val children = mutableListOf<Renderable>()

    fun h1(code: H1Code.() -> Unit) {
        children += H1Code().apply(code)
    }

    override fun render(stringBuilder: StringBuilder) {
        stringBuilder += Tag.Body wrap children.render()
    }
}

class H1Code : Renderable {
    private var value = ""
    operator fun String.unaryPlus() {
        value = this
    }

    override fun render(stringBuilder: StringBuilder) {
        stringBuilder += Tag.H1 wrap value
    }
}
