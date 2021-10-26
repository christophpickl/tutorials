package nuggets.any

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.scopes.StringSpecScope
import io.kotest.matchers.shouldBe

data class Auction(
    val title: String,
    val age: Int
    // now try to add a property here, and see what happens (or change property type, remove it, et cetera)
) {
    companion object
}

object AuctionService {
    fun isOldEnough(auction: Auction) = auction.age > 18
}

class SomeAuctionTest : StringSpec() {
    init {
        "is old enough - too verbose" {
            // title defined here is irelevant, should be hidden! :-/
            val auction = Auction("foo", 12)

            val result = AuctionService.isOldEnough(auction)

            result shouldBe false
        }
        "is old enough - coupling" {
            // this requires to have a StringSpec scope, making it not reusable in any other context/test type! :-(
            val auction = newAuction()

            val result = AuctionService.isOldEnough(auction)

            result shouldBe false
        }
        "is old enough - improved" {
            // hide the irrelevant, and only state what's relevant for this specific test case :-)
            val auction = Auction.any().copy(age = 12)

            val result = AuctionService.isOldEnough(auction)

            result shouldBe false
        }
    }
}

// IDE warns in full justification about an unused receiver parameter, as we introduce a totally unnecessary coupling here! :-(
fun StringSpecScope.newAuction() = Auction("foo", 10)

fun createAuction() = Auction("foo", 10)
fun makeAuction() = Auction("foo", 10)
fun buildAuction() = Auction("foo", 10)

// while refactoring type name, this function stayed oldschool :-/
fun createAuctionItem() = Auction("foo", 10)

fun Auction.Companion.any() = Auction(
    title = "anyTitle",
    age = -1
)

// seems to solve some issue, but actually not refactoring safe: in case property definitions change, have to do duplicate work! :-/
fun createAuction(
    // these arguments could be also considered to be moved into the any() method, would still lead to the some problem though :-/
    title: String = "defaultTitle",
    age: Int = 42
) = Auction(
    title = title,
    age = age
)

class IntegrationTestUtil(val database: Any) {
    // this guy is (unnecessarily) amazingly difficult to reach! :-/
    fun createAuction() = Auction("", 1)
}
