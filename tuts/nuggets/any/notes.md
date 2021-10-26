# Goal

* Writing not only tests, but also **good** tests - **maintainability!**
* Tests as (living) **documentation**.
  * A test should read like a young boy's novel, self explanatory, nothing else to look into but the test to understand the functionality
* Everything which is relevant **explicitly** stated, everything else abstracted away - **focus!**
  * Do not rely on **implicit** test data (shared test data is considered a hell); these values could change at any given time!
  * Deliberately set values in `any()` to something invalid, "forcing" the test to set it manually.
  * Set strings like `title` to `"anyTitle"` so it is clear that this value is used for that property (handy while debugging).
* Lowest **coupling** possible: Instantiating a test instance should not rely on anything.
  * E.g.: Specific subclass, util class instantiation, StringSpec extension function, et cetera.
  * So it can be reused in any kind of tests without any limiting requirement - **reusability!**
* Maintain **reliable** tests (avoid flakyness) by not using any **randomness**.
  * I already stepped into this trap with random dates (now) and formatting.
  * Random UUID is a corner case, still not preferrable + the test should explicitly state it's needs.
  * BUT: **Acceptance tests** are different, there implicit random IDs are an essential necessity

# Q&A

* Why not just `createAuction()`?
  * IntelliSense FTW!
  * Extension functions are a major advantage of Kotlin over Java et al.
  * The brain's intuitive flow is subject-predicate-object, and this style is aligned with how our cognition works.
  * Instead of `SomeUtil.doSome(auction)` we rather do `auction.doSome()` which comes more intuitive.
  * When having a bunch of `create***()` functions, the list can get easily messy (doesn't scale).
  * This requires an (informal) convention of always using the same prefix for these functions, whereas `any()` is always the same.

* What about additional **memory consumption**? First doing `any()` then immediately `copy()`.
  * Indeed, there is an additional, unnecessary creation of an object.
  * We could return a pre-instantiated object in the function, avoiding instance recreation; BUT: No, unsafe coupling!
  * There might be a few bytes memory allocated, for a whole test suite maybe a few kilobytes -> neglectable, compared to the JVM itself and integration tests.
  * Don't be dogmatic, but be **pragmatic**: What are our real issues, and solve them - **premature optimization!**

* Why not pass **arguments** in instantiation method?
  * Long story short: **Refactoring** safety, keeping maintenance effort low.
  * If member properties change, then it is safer to let the generated `copy()` method do the plumbing.
  * In case properties change, there should be only one location be affected in test code!

# Other downsides?

* The need of a **companion** object.
  * **Production** code needs (tiny) change because of test code.
  * For value classes, this might prevent compiler to **inline** code!
  * For **3rd party** (Java) objects this is impossible.
  * PS: Arrow guys with optics (lenses) have the same issue.
