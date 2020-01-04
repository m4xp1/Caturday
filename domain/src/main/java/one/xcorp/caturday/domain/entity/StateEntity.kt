package one.xcorp.caturday.domain.entity

sealed class StateEntity<out I, out R> {

    data class Running<out I>(
        val info: I? = null
    ) : StateEntity<I, Nothing>()

    data class Failed(
        val error: Throwable,
        val retry: (() -> Unit)? = null
    ) : StateEntity<Nothing, Nothing>()

    data class Success<out R>(
        val result: R
    ) : StateEntity<Nothing, R>()
}
