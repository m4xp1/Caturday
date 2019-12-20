package one.xcorp.caturday.presenter

import one.xcorp.caturday.view.BaseView

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected var view: V? = null
        private set

    override var isAttached = view != null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }
}
