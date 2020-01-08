package one.xcorp.caturday.screen.cats.list

import androidx.paging.PagedList
import one.xcorp.caturday.BasePresenter
import one.xcorp.caturday.BaseState
import one.xcorp.caturday.BaseView
import one.xcorp.caturday.screen.cats.list.model.CatModel
import one.xcorp.caturday.screen.cats.list.model.StateModel

interface CatsListContract {

    interface View : BaseView {

        fun showCatsListState(state: StateModel)

        fun showCatsList(list: PagedList<CatModel>)
    }

    interface State : BaseState {

        val initialPosition: Int
    }

    interface Presenter : BasePresenter<View, State> {

        override fun getState(): State

        fun invalidate()
    }
}
