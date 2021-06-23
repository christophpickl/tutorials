package tutorial.arrow

data class Bad(val message: String) {
    init {
        println("new Bad: $message")
    }
}

data class Good(val message: String) {
    init {
        println("new Good: $message")
    }
}
