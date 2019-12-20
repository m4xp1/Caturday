package one.xcorp.caturday.di

import dagger.Subcomponent
import one.xcorp.caturday.screen.cats.list.CatsListActivity
import ru.cardsmobile.mw3.barch.presentation.di.scope.ActivityScope

@ActivityScope
@Subcomponent
interface CatsListComponent {

    fun inject(activity: CatsListActivity)
}
