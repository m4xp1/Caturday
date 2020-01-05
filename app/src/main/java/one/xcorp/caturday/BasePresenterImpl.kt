package one.xcorp.caturday

import rx.subscriptions.CompositeSubscription

abstract class BasePresenterImpl<V : BaseView, S : BaseState> : BasePresenter<V, S> {

    protected var view: V? = null
        private set

    protected val compositeSubscription = CompositeSubscription()

    override fun attach(view: V) {
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
