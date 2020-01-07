package one.xcorp.caturday.dagger.holder

interface ComponentHolder<Factory : Any, Component : Any> {

    fun getComponent(initializer: Factory.() -> Component): Component

    fun release()
}
