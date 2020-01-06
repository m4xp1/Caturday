package one.xcorp.caturday.screen.cats.list.model

import androidx.annotation.StringRes

sealed class StateModel {

    data class Running(
        val isInitial: Boolean
    ) : StateModel()

    data class Failed(
        @StringRes val message: Int,
        val retry: () -> Unit
    ) : StateModel()

    object Success : StateModel()
}
