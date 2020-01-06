package one.xcorp.caturday.dagger.holder

import one.xcorp.caturday.dagger.injector.Injector

fun <Factory : Any, Component : Any> ComponentHolder<Factory, Component>.injectWith(
    injector: Injector<Component>,
    initializer: Factory.() -> Component
): ComponentHolder<Factory, Component> {
    injector(get { initializer(this) })
    return this
}
