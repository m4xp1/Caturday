package one.xcorp.caturday.screen.cats.list.mapper

import one.xcorp.caturday.R
import one.xcorp.caturday.data.paging.RxPositionalDataSource.Info
import one.xcorp.caturday.domain.entity.CatEntity
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.entity.StateEntity
import one.xcorp.caturday.screen.cats.list.model.StateModel
import java.net.UnknownHostException

fun StateEntity<Info, PageEntity<CatEntity>>.toStateModel() = when (this) {
    is StateEntity.Running -> StateModel.Running(info!!.isInitial)
    is StateEntity.Failed -> {
        val message = if (error is UnknownHostException) {
            R.string.message_check_network_connection
        } else {
            R.string.message_can_not_load_data
        }
        StateModel.Failed(message, retry!!)
    }
    is StateEntity.Success -> StateModel.Success
}
