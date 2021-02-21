package fr.pk.championshipmanagerapplication

sealed class Either<out L, out R> {
    data class Left<out L>(val left: L) : Either<L, Nothing>()
    data class Right<out R>(val right: R) : Either<Nothing, R>()

    fun fold(ifLeft: (L) -> Any, ifRight: (R) -> Any): Any =
            when (this) {
                is Left -> ifLeft(left)
                is Right -> ifRight(right)
            }
}

inline fun <reified R> R.right() = Either.Right(this)
inline fun <reified L> L.left() = Either.Left(this)


fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
        when (this) {
            is Either.Left -> Either.Left(left)
            is Either.Right -> fn(right)
        }

fun <T, L, R> List<Either<L, R>>.flatMap(fn: (R) -> Either<L, T>): List<Either<L, T>> = this.map { it.flatMap(fn) }

fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
        this.apply { if (this is Either.Left) fn(left) }

/**
 * Right-biased onSuccess() FP convention dictates that when this class is Right, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
        this.apply { if (this is Either.Right) fn(right) }
