package one.xcorp.caturday.dagger

import dagger.BindsInstance
import dagger.Subcomponent
import one.xcorp.didy.holder.SingleComponentHolder
import one.xcorp.caturday.dagger.module.CatsListModule
import one.xcorp.caturday.screen.cats.list.CatsListActivity
import one.xcorp.caturday.screen.cats.list.CatsListContract
import one.xcorp.caturday.dagger.scope.ActivityScope

@ActivityScope
@Subcomponent(
    modules = [
        CatsListModule::class
    ]
)
interface CatsListComponent {

    fun inject(instance: CatsListActivity)

    @Subcomponent.Factory
    interface Factory {

        fun createComponent(@BindsInstance state: CatsListContract.State?): CatsListComponent
    }

    class Holder(factory: Factory) : SingleComponentHolder<Factory, CatsListComponent>(factory)
}
