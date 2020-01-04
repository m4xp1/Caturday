package one.xcorp.caturday

interface BasePresenter<V : BaseView, S : BaseState> {

    var isAttached: Boolean

    fun attach(view: V, state: S?)

    fun getState(): S?

    fun detach()

    fun dispose()
}
