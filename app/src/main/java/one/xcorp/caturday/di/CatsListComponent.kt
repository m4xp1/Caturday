package one.xcorp.caturday.di

import dagger.Subcomponent
import one.xcorp.caturday.di.module.CatsListModule
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
