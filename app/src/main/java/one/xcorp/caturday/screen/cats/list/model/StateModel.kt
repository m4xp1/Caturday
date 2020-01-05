package one.xcorp.caturday.screen.cats.list.model

sealed class StateModel {

    data class Running(
        val isInitial: Boolean
    ) : StateModel()

    data class Failed(
        val message: Int,
        val retry: () -> Unit
    ) : StateModel()

    object Success : StateModel()
}
