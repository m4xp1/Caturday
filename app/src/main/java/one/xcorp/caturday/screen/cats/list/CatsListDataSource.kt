package one.xcorp.caturday.screen.cats.list

import androidx.paging.PositionalDataSource
import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.OrderEntity.ASCENDING
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.usecase.GetCatsImagesUseCase
import one.xcorp.caturday.mapper.toCatModel
import one.xcorp.caturday.model.CatModel
import one.xcorp.caturday.model.StatusModel
import one.xcorp.caturday.model.StatusModel.Failed
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject

class CatsListDataSource(
    private val getCatsImages: GetCatsImagesUseCase,
    private val order: OrderEntity = ASCENDING
) : PositionalDataSource<CatModel>() {

    private val statusSubject = PublishSubject.create<StatusModel>()
    private val statusObservable = statusSubject
        .observeOn(AndroidSchedulers.mainThread())

    private val loadSubject = PublishSubject.create<Request>()
    private val loadObservable = loadSubject
        .subscribeOn(Schedulers.io())
        .doOnNext { statusSubject.onNext(StatusModel.Running) }
        .flatMap(::loadData)
        .doOnNext { statusSubject.onNext(StatusModel.Success) }
        .subscribe { (request, page) ->
            request.callback(page)
        }

    fun isDisposed(): Boolean = loadObservable.isUnsubscribed

    fun observeStatus(): Observable<StatusModel> = statusObservable

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CatModel>) {
        val request = Request(params.requestedLoadSize, params.requestedStartPosition, order) {
            callback.onResult(it.items.toCatModel(), it.startItem, it.totalItems)
        }
        loadSubject.onNext(request)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CatModel>) {
        val request = Request(params.loadSize, params.startPosition, order) {
            callback.onResult(it.items.toCatModel())
        }
        loadSubject.onNext(request)
    }

    fun dispose() = loadObservable.unsubscribe()

    private fun loadData(
        request: Request
    ): Observable<Pair<Request, PageEntity<CatImageEntity>>> {
        return getCatsImages(request.limit, request.position, request.order).toObservable()
            .doOnError {
                statusSubject.onNext(Failed(it) {
                    loadSubject.onNext(request)
                })
            }
            .onErrorResumeNext(Observable.empty())
            .map { Pair(request, it) }
    }

    private data class Request(
        val limit: Int,
        val position: Int,
        val order: OrderEntity,
        val callback: (PageEntity<CatImageEntity>) -> Unit
    )
}
