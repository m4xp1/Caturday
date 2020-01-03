package one.xcorp.caturday.dagger.module

import dagger.Binds
import dagger.Module
import one.xcorp.caturday.screen.cats.list.CatsListContract
import one.xcorp.caturday.screen.cats.list.CatsListPresenter
import ru.cardsmobile.mw3.barch.presentation.di.scope.ActivityScope

@Module
abstract class CatsListModule {

    @Binds
    @ActivityScope
    abstract fun bindPresenter(presenter: CatsListPresenter): CatsListContract.Presenter
}
