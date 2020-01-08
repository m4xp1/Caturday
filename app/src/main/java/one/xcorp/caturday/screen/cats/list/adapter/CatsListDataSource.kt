package one.xcorp.caturday.screen.cats.list.adapter

import one.xcorp.caturday.data.paging.RxPositionalDataSource
import one.xcorp.caturday.data.paging.RxPositionalDataSource.State.Success
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.OrderEntity.ASCENDING
import one.xcorp.caturday.domain.usecase.GetCatsListUseCase
import one.xcorp.caturday.screen.cats.list.mapper.toCatModel
import one.xcorp.caturday.screen.cats.list.model.CatModel
import rx.Single

class CatsListDataSource(
    private val getCatsList: GetCatsListUseCase,
    private val order: OrderEntity = ASCENDING
) : RxPositionalDataSource<CatModel>() {

    override fun loadData(
        size: Int,
        position: Int,
        isInitial: Boolean
    ): Single<Success<CatModel>> {
        return getCatsList(size, position, order, isInitial)
            .map { Success(it.items.toCatModel(), it.position, it.totalCount) }
    }
}
