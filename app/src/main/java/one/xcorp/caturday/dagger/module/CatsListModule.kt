package one.xcorp.caturday.dagger.module

import dagger.Module
import dagger.Provides
import one.xcorp.caturday.data.paging.RxPositionalDataSource
import one.xcorp.caturday.domain.usecase.GetCatsListUseCase
import one.xcorp.caturday.screen.cats.list.CatsListContract
import one.xcorp.caturday.screen.cats.list.CatsListPresenter
import one.xcorp.caturday.screen.cats.list.adapter.CatsListDataSource
import one.xcorp.caturday.screen.cats.list.model.CatModel
import ru.cardsmobile.mw3.barch.presentation.di.scope.ActivityScope

@Module
class CatsListModule {

    @Provides
    fun catsListDataSource(useCase: GetCatsListUseCase): RxPositionalDataSource<CatModel> =
        CatsListDataSource(useCase)

    @Provides
    @ActivityScope
    fun catsListPresenter(presenter: CatsListPresenter): CatsListContract.Presenter = presenter
}
