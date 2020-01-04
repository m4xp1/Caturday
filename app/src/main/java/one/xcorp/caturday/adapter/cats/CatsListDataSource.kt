package one.xcorp.caturday.adapter.cats

import one.xcorp.caturday.adapter.RxPositionalDataSource
import one.xcorp.caturday.domain.entity.CatEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.OrderEntity.ASCENDING
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.usecase.GetCatsListUseCase
import one.xcorp.caturday.mapper.toCatModel
import one.xcorp.caturday.model.CatModel
import rx.Single
import rx.subscriptions.CompositeSubscription

class CatsListDataSource(
    compositeSubscription: CompositeSubscription,
    private val getCatsImages: GetCatsListUseCase,
    private val order: OrderEntity = ASCENDING
) : RxPositionalDataSource<CatModel, PageEntity<CatEntity>>(compositeSubscription) {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CatModel>) {
        loadData(Request(params.requestedLoadSize, params.requestedStartPosition, true)) {
            callback.onResult(items.toCatModel(), startPosition, totalCount)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CatModel>) {
        loadData(Request(params.loadSize, params.startPosition, false)) {
            callback.onResult(items.toCatModel())
        }
    }

    override fun loadDataSingle(request: Request): Single<PageEntity<CatEntity>> =
        getCatsImages(request.size, request.position, order, request.isInitial)
}
