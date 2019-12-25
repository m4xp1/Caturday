package one.xcorp.caturday.presenter

import one.xcorp.caturday.view.BaseView
import rx.subscriptions.CompositeSubscription

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected val compositeSubscription = CompositeSubscription()

    protected var view: V? = null
        private set

    override var isAttached = view != null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun dispose() {
        compositeSubscription.unsubscribe()
    }
}
