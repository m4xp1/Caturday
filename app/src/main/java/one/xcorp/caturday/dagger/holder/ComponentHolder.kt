package one.xcorp.caturday.dagger.holder

interface ComponentHolder<Factory : Any, Component : Any> {

    fun get(initializer: Factory.() -> Component): Component

    fun get(): Component

    fun release()
}
