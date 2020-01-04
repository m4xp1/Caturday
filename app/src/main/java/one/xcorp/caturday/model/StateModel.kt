package one.xcorp.caturday.model

sealed class StateModel<out I, out R> {

    data class Running<out I>(
        val info: I? = null
    ) : StateModel<I, Nothing>()

    data class Failed(
        val error: Throwable,
        val retry: (() -> Unit)? = null
    ) : StateModel<Nothing, Nothing>()

    data class Success<out R>(
        val result: R
    ) : StateModel<Nothing, R>()
}
