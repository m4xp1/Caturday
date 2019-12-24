package one.xcorp.caturday.datasource.cats

import one.xcorp.caturday.datasource.RxPositionalDataSource
import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.OrderEntity.ASCENDING
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.usecase.GetCatsImagesUseCase
import one.xcorp.caturday.mapper.toCatModel
import one.xcorp.caturday.model.CatModel
import rx.Observable

class CatsListDataSource(
    private val getCatsImages: GetCatsImagesUseCase,
    private val order: OrderEntity = ASCENDING
) : RxPositionalDataSource<CatModel, PageEntity<CatImageEntity>>() {

    override fun initialRequest(
        params: LoadInitialParams,
        callback: LoadInitialCallback<CatModel>
    ) = Request(params.requestedLoadSize, params.requestedStartPosition, order) {
        callback.onResult(items.toCatModel(), startItem, totalItems)
    }

    override fun rangeRequest(
        params: LoadRangeParams,
        callback: LoadRangeCallback<CatModel>
    ) = Request(params.loadSize, params.startPosition, order) {
        callback.onResult(items.toCatModel())
    }

    override fun loadData(request: Request): Observable<PageEntity<CatImageEntity>> {
        return getCatsImages(request.limit, request.position, request.order).toObservable()
    }
}
