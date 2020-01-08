package one.xcorp.caturday

import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

abstract class BasePresenterImpl<V : BaseView, S : BaseState> : BasePresenter<V, S> {

    protected var view: V? = null
        private set

    protected val presenterSubscriptions = CompositeSubscription()
    protected val viewSubscriptions = CompositeSubscription()

    init {
        presenterSubscriptions += viewSubscriptions
    }

    override fun attach(view: V) {
        this.view = view
    }

    override fun getState(): S? {
        return null
    }

    override fun detach() {
        viewSubscriptions.clear()
        view = null
    }

    override fun dispose() {
        presenterSubscriptions.unsubscribe()
    }
}
