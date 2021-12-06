import Location.WesternEurope

fun main() {
    val pipeline = pipeline {

        // parameters["shortLocation"] = "we"
        parameters.location(WesternEurope)

        parameters["serviceName"] = "checkout"

        job {
            parameters["azureSubscription"] = "\$(AzureSubscription\${{ parameters.environment }})"
            template = "Pipelines\\Templates\\azure-aksgetvaluesforsecret-template.yml@templates"
        }
    }
    println(pipeline)
}
/*
parameters:
  shortLocation: we
  serviceName: checkout
jobs:
  - template: Pipelines\Templates\azure-aksgetvaluesforsecret-template.yml@templates
  parameters:
    azureSubscription: $(AzureSubscription${{ parameters.environment }})
*/

// ==================================================================================================
// custom, type-safe extensions

enum class Location(val shortCode: String) {
    WesternEurope("we")
}

fun Parameters.location(location: Location) {
    set("shortLocation", location.shortCode)
}

// ==================================================================================================
// core DSL

fun pipeline(code: PipelineDsl.() -> Unit) =
    Stringy().let {
        PipelineDsl().apply(code).render(it)
        it.buildString()
    }

@YamlDsl
class PipelineDsl : Renderable {
    val parameters = Parameters()
    private val jobs = mutableListOf<JobDsl>()

    fun job(code: JobDsl.() -> Unit) {
        jobs += JobDsl().apply(code)
    }

    override fun render(stringy: Stringy) {
        parameters.render(stringy)
        if (jobs.isNotEmpty()) {
            stringy.appendLine("jobs:")
            stringy.withIndented {
                jobs.renderAll(stringy)
            }
        }
    }
}

@YamlDsl
class JobDsl : Renderable {
    var template = ""
    val parameters = Parameters()

    override fun render(stringy: Stringy) {
        require(template.isNotEmpty())
        stringy.appendLine("- template: $template")
        parameters.render(stringy)
    }
}

// ==================================================================================================
// helpers, utils

interface Renderable {
    fun render(stringy: Stringy)
}

private fun List<Renderable>.renderAll(stringy: Stringy) {
    forEach {
        it.render(stringy)
    }
}

class Parameters : Renderable {
    private val values = mutableMapOf<String, String>()

    operator fun get(key: String) = values[key]
    operator fun set(key: String, value: String) {
        values[key] = value
    }

    override fun render(stringy: Stringy) {
        if (values.isNotEmpty()) {
            stringy.appendLine("parameters:")
            stringy.withIndented {
                values.forEach { (key, value) ->
                    stringy.appendLine("$key: $value")
                }
            }
        }
    }
}

@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
annotation class YamlDsl

class Stringy {
    private val buffer = StringBuilder()
    private val indentManager = Indent()

    val indent get() = indentManager.stringValue

    fun append(string: String) {
        buffer.append(string)
    }

    fun appendLine(string: String) {
        buffer.append(indentManager.stringValue).append(string).append("\n")
    }

    fun withIndented(code: () -> Unit) {
        +indentManager
        code()
        -indentManager
    }

    fun buildString() = buffer.toString()

    private class Indent {
        companion object {
            private const val token = "  "
        }

        private var level = 0
            set(value) {
                require(value >= 0)
                field = value
                stringValue = if (value == 0) {
                    ""
                } else {
                    (0 until value).fold("") { acc, _ -> "$token$acc" }
                }
            }
        var stringValue = ""
            private set

        operator fun unaryPlus() {
            level += 1
        }

        operator fun unaryMinus() {
            level -= 1
        }
    }
}
