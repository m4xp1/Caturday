package one.xcorp.caturday

import rx.subscriptions.CompositeSubscription

abstract class BasePresenterImpl<V : BaseView, S : BaseState> : BasePresenter<V, S> {

    protected val compositeSubscription = CompositeSubscription()

    protected var view: V? = null
        private set

    override var isAttached = view != null

    override fun attach(view: V, state: S?) {
        this.view = view
    }

    override fun getState(): S? {
        return null
    }

    override fun detach() {
        this.view = null
    }

    override fun dispose() {
        compositeSubscription.unsubscribe()
    }
}
