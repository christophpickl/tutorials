package tutorial.arrow.selfmade

import io.kotest.core.spec.style.StringSpec

sealed class Either<LeftType, RightType> {
    fun <NewLeftType> mapLeft(leftTransformer: (LeftType) -> NewLeftType): Either<NewLeftType, RightType> =
        when (this) {
            is Left -> Left(leftTransformer(element))
            is Right -> Right(element)
        }

    fun <NewRightType> mapRight(rightTransformer: (RightType) -> NewRightType): Either<LeftType, NewRightType> =
        when (this) {
            is Left -> Left(element)
            is Right -> Right(rightTransformer(element))
        }
}

data class Left<LeftType, RightType>(val element: LeftType) : Either<LeftType, RightType>() {
    override fun toString() = "Left[$element]"
}

data class Right<LeftType, RightType>(val element: RightType) : Either<LeftType, RightType>() {
    override fun toString() = "Right[$element]"
}

class EitherTest : StringSpec() {
    init {
        "fo" {
            val leftAndRight: List<Either<Int, Int>> = listOf(Left(1), Right(1))
            leftAndRight.forEach { either ->
                println("$either mapLeft: ${either.mapLeft { it * 2 }}")
                println("$either mapRight: ${either.mapRight { it * 2 }}")
            }
        }
    }
}