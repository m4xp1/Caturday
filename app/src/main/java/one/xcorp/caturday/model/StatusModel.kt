package one.xcorp.caturday.model

sealed class StatusModel {

    object Running : StatusModel()

    data class Failed(
        val error: Throwable,
        val retry: (() -> Unit)? = null
    ) : StatusModel()

    object Success : StatusModel()
}
