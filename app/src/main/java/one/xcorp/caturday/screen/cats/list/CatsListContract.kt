package one.xcorp.caturday.screen.cats.list

import one.xcorp.caturday.presenter.BasePresenter
import one.xcorp.caturday.screen.cats.list.model.CatModel
import one.xcorp.caturday.view.BaseView

interface CatsListContract {

    interface View : BaseView {

        fun showError(throwable: Throwable)

        fun showCats(items: List<CatModel>)
    }

    interface Presenter : BasePresenter<View> {

        fun nextPage()
    }
}
