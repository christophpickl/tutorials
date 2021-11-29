package fpkotlin

// https://www.raywenderlich.com/9527-functional-programming-with-kotlin-and-arrow-getting-started

data class Book(
    val price: Int
)

typealias Func<A, B> = (A) -> B

infix fun <A, B, C> Func<A, B>.andThen(f: Func<B, C>): Func<A, C> = { x: A -> f(this(x)) }

fun main() {
    val getPrice: Func<Book, Int> = { book -> book.price }
    val formatPrice: Func<Int, String> = fun(price: Int) = "EUR $price"

    val formattedPrice = (getPrice andThen formatPrice)(Book(42))
    println(formattedPrice)

//    // with arrow:
//    val formattedPrice2 = (formatPrice compose getPrice)(Book(42))
//    println(formattedPrice2)
}
