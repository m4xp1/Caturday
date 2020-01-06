package one.xcorp.caturday.dagger.holder

abstract class SingleComponentHolder<Factory : Any, Component : Any>(
    private val factory: Factory
) : ComponentHolder<Factory, Component> {

    private var instance: Component? = null

    override fun get(initializer: Factory.() -> Component): Component = synchronized(this) {
        instance ?: initializer(factory).also { instance = it }
    }

    override fun release() = synchronized(this) {
        instance = null
    }
}
