package tutorial.kotest

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.AssertionMode
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.collections.shouldBeSmallerThan
import io.kotest.matchers.collections.shouldBeSorted
import io.kotest.matchers.collections.shouldBeUnique
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainAnyOf
import io.kotest.matchers.collections.shouldContainDuplicates
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldContainNull
import io.kotest.matchers.collections.shouldEndWith
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveLowerBound
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldHaveUpperBound
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.collections.shouldNotHaveElementAt
import io.kotest.matchers.collections.shouldStartWith
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.date.shouldBeToday
import io.kotest.matchers.date.shouldHaveSameHoursAs
import io.kotest.matchers.doubles.Percentage
import io.kotest.matchers.doubles.beNaN
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.doubles.shouldBeNaN
import io.kotest.matchers.doubles.shouldNotBeNaN
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.equality.shouldBeEqualToComparingFieldsExcept
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.equality.shouldBeEqualToUsingFields
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAbsolute
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.file.shouldNotBeEmpty
import io.kotest.matchers.ints.beOdd
import io.kotest.matchers.ints.shouldBeBetween
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.ints.shouldBeOdd
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.iterator.shouldBeEmpty
import io.kotest.matchers.iterator.shouldHaveNext
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.maps.shouldContainAll
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.beEmpty
import io.kotest.matchers.string.shouldBeBlank
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.kotest.matchers.string.shouldBeInteger
import io.kotest.matchers.string.shouldBeLowerCase
import io.kotest.matchers.string.shouldBeUpperCase
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainADigit
import io.kotest.matchers.string.shouldContainIgnoringCase
import io.kotest.matchers.string.shouldContainOnlyDigits
import io.kotest.matchers.string.shouldContainOnlyOnce
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldHaveLineCount
import io.kotest.matchers.string.shouldHaveMaxLength
import io.kotest.matchers.string.shouldHaveMinLength
import io.kotest.matchers.string.shouldHaveSameLengthAs
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldNotBeEmpty
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.throwable.shouldHaveCause
import io.kotest.matchers.throwable.shouldHaveCauseInstanceOf
import io.kotest.matchers.throwable.shouldHaveCauseOfType
import io.kotest.matchers.throwable.shouldHaveMessage
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeTypeOf
import io.kotest.matchers.uri.shouldHaveHost
import io.kotest.matchers.uri.shouldHavePort
import io.kotest.matchers.uri.shouldHaveScheme
import java.io.File
import java.net.URI
import java.time.LocalDate
import java.time.LocalTime

// https://kotest.io/docs/assertions/core-matchers.html
class MatchersTest : DescribeSpec({

    describe("general") {

        it("basics") {
            (1 == 1).shouldBeTrue()
            (2 + 2) shouldBe 4

            val foo: Any = "foobar"
            foo.shouldBeTypeOf<String>() shouldContain "fo"

            "".shouldBeEmpty()
            "x".shouldNot(beEmpty()) // manually negate
            "x".shouldNotBeEmpty() // reusable

            URI("https://tba") shouldHaveHost "tba"
            URI("https://tba:81") shouldHavePort 81
            URI("https://tba") shouldHaveScheme "https"

            File("/").apply {
                shouldExist()
                shouldBeADirectory()
                shouldBeAbsolute()
                shouldNotBeEmpty()
            }
            // executable, hidden, readable, smaller, writeable, containFile, extension, path, ...

            LocalDate.now().shouldBeToday()
            // before/after, within, same, between, have year/month/day/hour/...
            LocalTime.now().shouldHaveSameHoursAs(LocalTime.now())
            // before/after/between, sameMinute/Seconds/Nanos
        }

        it("numbers") {
            1 shouldBeLessThan 2
            1 shouldBeLessThanOrEqual 1 // Int-based; returns this
            1 shouldBeLessThanOrEqualTo 1 // Comparble-based; void
            1 shouldBeEqualComparingTo 1 // Comparable-based
            1.shouldBeBetween(0, 2)
            1 shouldBeInRange 0..2
            0.shouldBeZero()
            1.shouldBePositive()
            1.shouldBeOdd()
            (1.2).shouldBe(1.20001.plusOrMinus(Percentage(20.0)))
            (1.2).shouldNotBeNaN()
        }
        it("strings") {
            // generic: "abc" shouldBe "abc"
            "aBc" shouldBeEqualIgnoringCase "abc"
            "".shouldBeEmpty()
            " ".shouldBeBlank() // empty or whitespace
            "abc" shouldContain ("b")
            "aBc" shouldContainIgnoringCase "bc"
            "x-a-x" shouldContain """\-[a-z]\-""".toRegex()
            "-a-" shouldMatch """\-[a-z]\-""".toRegex()

            "abc" shouldStartWith ("a")
            "abc" shouldEndWith ("c")
            "ab aa" shouldContainOnlyOnce "aa"

            "abc".shouldBeLowerCase()
            "ABC".shouldBeUpperCase()

            "abc" shouldHaveLength 3
            "a\nb" shouldHaveLineCount 2
            "ab" shouldHaveMinLength 1 shouldHaveMaxLength 3
            "abc" shouldHaveSameLengthAs "foo"

            "1".shouldBeInteger()
            "12".shouldContainOnlyDigits()
            "abc1".shouldContainADigit() // at least one
        }

        it("types") {
            @Connotation
            open class SuperType()
            class SubType : SuperType()

            val sameRef = SuperType()
            sameRef.shouldBeSameInstanceAs(sameRef)

            val superType: SuperType = SubType()
            superType.shouldBeTypeOf<SubType>() // exact runtime match (SuperType won't work!)
            superType.shouldBeInstanceOf<SuperType>() // T or below

//            SubType().shouldHaveAnnotation(Connotation::class)

            val nullable: String? = null
            nullable.shouldBeNull()
        }

        it("collections") {
            emptyList<Int>().iterator().shouldBeEmpty()
            listOf(1).iterator().shouldHaveNext()

            listOf(1, 2) shouldContain 1 // at least
            listOf(1, 2) shouldContainExactly listOf(1, 2) // in-order; not more
            listOf(1, 2) shouldContainExactlyInAnyOrder listOf(2, 1) // out-order; not more
            listOf(0, 3, 0, 4, 0).shouldContainInOrder(3, 4) // possible items in between
            listOf(1) shouldNotContainAnyOf listOf(2, 3) // black list
            listOf(1, 2, 3) shouldContainAll listOf(3, 2) // out-order; more
            listOf(1, 2, 3).shouldBeUnique() // no duplicates
            listOf(1, 2, 2).shouldContainDuplicates() // at least one duplicate
            listOf(1, 2).shouldNotHaveElementAt(1, 3)
            listOf(1, 2) shouldStartWith 1
            listOf(1, 2) shouldEndWith 2
            listOf(1, 2) shouldContainAnyOf listOf(2, 3)
            val x = SomeType(1)
            x shouldBeOneOf listOf(x) // by reference/instance
            x shouldBeIn listOf(SomeType(1)) // by equality/structural

            listOf(1, 2, null).shouldContainNull()
            listOf(1) shouldHaveSize 1
            listOf(1).shouldBeSingleton() // size == 1
            listOf(1).shouldBeSingleton {
                it.shouldBeOdd()
            }
            listOf(1).shouldHaveSingleElement {
                beOdd().test(it).passed() // have to return a boolean here :-/
            }
            listOf(2, 3) shouldHaveLowerBound 1 shouldHaveUpperBound 4
            listOf(1) shouldBeSmallerThan listOf(1, 2)
            listOf(1) shouldBeSameSizeAs listOf(2)
            listOf(1, 2) shouldHaveAtLeastSize 1
            listOf(1, 2).shouldBeSorted()


            mapOf(1 to "a").shouldContain(1, "a") // at least this
            mapOf(1 to "a") shouldContainAll mapOf(1 to "a") // at least those
            mapOf(1 to "a") shouldContainExactly mapOf(1 to "a") // not more
            mapOf(1 to "a") shouldContainKey 1
            emptyMap<Any, Any>().shouldBeEmpty()
        }

        it("exceptions") {
            class SubException() : Exception()

            Exception("a") shouldHaveMessage "a" // same (not contains!)
            Exception("abc").message shouldContain "b"
            Exception("", Exception()).shouldHaveCause()
            Exception("symptom", Exception("cause")).shouldHaveCause {
                it.message shouldBe "cause"
            }
            Exception("", SubException()).shouldHaveCauseInstanceOf<Exception>() // T or subclass
            Exception("", SubException()).shouldHaveCauseOfType<SubException>() // exactly T

            shouldThrow<Exception> { // type or subtype
                throw SubException()
            }

            shouldThrowExactly<Exception> { // exactly that type (no subtype!)
                throw Exception()
            }

            shouldThrowAny { // don't care which
                throw Exception()
            }
        }
    }

    describe("advanced") {
        it("selective matcheres") {
            data class Foo(val p1: Int, val p2: Int)

            val foo1 = Foo(1, 1)
            val foo2 = Foo(1, 2)

            foo1.shouldBeEqualToUsingFields(foo2, Foo::p1)
            foo1.shouldBeEqualToIgnoringFields(foo2, Foo::p2)

            class Bar(val p1: Int, val p2: Int) // not a data class! no equals.

            val bar1a = Bar(1, 1)
            val bar1b = Bar(1, 1)
            bar1a shouldNotBe bar1b
            bar1a shouldBeEqualToComparingFields bar1b // "fake equals" (ignoring private properties)
            bar1a.shouldBeEqualToComparingFields(bar1b, false) // explicitly also check private props
            val bar2 = Bar(1, 2)
            bar1a.shouldBeEqualToComparingFieldsExcept(bar2, Bar::p2)
        }
    }
    // channels
    // concurrent, futures
    // result, optional
    // threads
    // reflection
    // statistic, regex
})

private data class SomeType(val value: Int = 1)
private annotation class Connotation


