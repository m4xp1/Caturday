package one.xcorp.caturday

interface BasePresenter<V : BaseView, S : BaseState> {

    fun onCreate(state: S?)

    fun attach(view: V)

    fun getState(): S?

    fun detach()

    fun dispose()
}
