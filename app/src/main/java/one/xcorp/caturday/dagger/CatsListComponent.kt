package one.xcorp.caturday.dagger

import dagger.Subcomponent
import one.xcorp.caturday.dagger.module.CatsListModule
import one.xcorp.caturday.screen.cats.list.CatsListActivity
import ru.cardsmobile.mw3.barch.presentation.di.scope.ActivityScope

@ActivityScope
@Subcomponent(
    modules = [
        CatsListModule::class
    ]
)
interface CatsListComponent {

    fun inject(activity: CatsListActivity)
}
