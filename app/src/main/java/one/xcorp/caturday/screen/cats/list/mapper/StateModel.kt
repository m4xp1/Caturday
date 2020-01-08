package one.xcorp.caturday.screen.cats.list.mapper

import one.xcorp.caturday.R
import one.xcorp.caturday.data.paging.RxPositionalDataSource
import one.xcorp.caturday.screen.cats.list.model.CatModel
import one.xcorp.caturday.screen.cats.list.model.StateModel.*
import java.net.UnknownHostException

fun RxPositionalDataSource.State<CatModel>.toStateModel() = when (this) {
    is RxPositionalDataSource.State.Running -> Running(isInitial)
    is RxPositionalDataSource.State.Failed -> {
        val message = if (error is UnknownHostException) {
            R.string.message_check_network_connection
        } else {
            R.string.message_can_not_load_data
        }
        Failed(message, retry)
    }
    is RxPositionalDataSource.State.Success -> Success
}
