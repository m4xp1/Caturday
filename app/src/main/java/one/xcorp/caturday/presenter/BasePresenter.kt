package one.xcorp.caturday.presenter

import one.xcorp.caturday.view.BaseView

interface BasePresenter<V : BaseView> {

    var isAttached: Boolean

    fun attach(view: V)

    fun detach()

    fun dispose()
}
