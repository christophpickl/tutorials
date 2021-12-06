package fpkotlin.ch5_functions2

fun String.`extension function with receiver type being string`() {
    // actually compiles to a static function with it's first parameter being the string ;)
    // restriction: it can't access private stuff (like a regular method/member function)
}

// ================================================================================================

open class Animal {
    open fun makeSound() = "generic sound"
}

class Cat : Animal() {
    override fun makeSound() = "meow"
}

fun animalSound(animal: Animal) {
    println("Animal says: ${animal.makeSound()}")
}

open class AnimalX

fun AnimalX.makeSound() = "generic X sound"
open class CatX : AnimalX()

fun CatX.makeSound() = "cat X sound"
fun animalxSound(animal: AnimalX) {
    println("AnimalX says: ${animal.makeSound()}")
}

fun `showcase extension function VS inheritance`() {
    animalSound(Animal()) // "generic sound"
    animalSound(Cat()) // "meow"

    animalxSound(AnimalX()) // "generic X sound"
    animalxSound(CatX()) // "generic X sound"!!! there is no inheritance here, just some static (!no dynamic dispatching!) toplevel functions receiving the dynamic runtime type
}

// ================================================================================================

class Bottom(val name: String)
open class Top(val name: String) {
    open val Bottom.greeting get() = "Hello $name for ${this@Top.name}."
    fun exec(bottom: Bottom) {
        println(bottom.greeting)
    }
}

class Top2(name: String) : Top(name) {
    override val Bottom.greeting get() = "Hoi $name!"
}

fun `showcase dispatch receiver`() {
    val bottom = Bottom("sub name")
    Top("top name").exec(bottom) // "Hello sub name for top name."
    Top2("top name").exec(bottom) // "Hoi sub name!"
}

fun main() {
    `showcase overloading shadowing and stuff`()
}

// ================================================================================================

class Clashing {
    fun publicFun() = "public class"
    private fun privateFun() = "private class"
}

fun Clashing.publicFun() = "public extension" // only warning, class method will have precedence!
fun Clashing.privateFun() = "private extension" // private method will not be used, instead this one here
fun Clashing.publicFun(overloaded: Any) = "public overloaded extension"

fun `showcase overloading shadowing and stuff`() {
    println(Clashing().publicFun()) // "public class"
    println(Clashing().privateFun()) // "private extension"
    Clashing().publicFun("overladed extension function will be used")
}
