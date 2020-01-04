package one.xcorp.caturday.data.paging

sealed class State<out I, out R> {

    data class Running<out I>(
        val info: I? = null
    ) : State<I, Nothing>()

    data class Failed(
        val error: Throwable,
        val retry: (() -> Unit)? = null
    ) : State<Nothing, Nothing>()

    data class Success<out R>(
        val result: R
    ) : State<Nothing, R>()
}
