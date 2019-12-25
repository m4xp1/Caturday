package one.xcorp.caturday.datasource

import androidx.paging.PositionalDataSource
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.model.StatusModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

abstract class RxPositionalDataSource<T, R>(
    compositeSubscription: CompositeSubscription
) : PositionalDataSource<T>() {

    private val statusSubject = PublishSubject.create<StatusModel>()
    private val statusObservable = statusSubject
        .observeOn(AndroidSchedulers.mainThread())

    private val loadSubject = PublishSubject.create<Request>()
    private val loadSubscription = loadSubject
        .doOnNext { statusSubject.onNext(StatusModel.Running) }
        .flatMap { request ->
            loadData(request)
                .doOnError {
                    statusSubject.onNext(StatusModel.Failed(it) {
                        loadSubject.onNext(request)
                    })
                }
                .onErrorResumeNext(Observable.empty())
                .map { Pair(request, it) }
        }
        .doOnNext { statusSubject.onNext(StatusModel.Success) }
        .subscribe { (request, value) ->
            request.callback(value)
        }

    init {
        compositeSubscription.add(loadSubscription)
    }

    fun observeStatus(): Observable<StatusModel> = statusObservable

    final override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) =
        loadSubject.onNext(initialRequest(params, callback))

    final override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) =
        loadSubject.onNext(rangeRequest(params, callback))

    protected abstract fun initialRequest(
        params: LoadInitialParams,
        callback: LoadInitialCallback<T>
    ): Request

    protected abstract fun rangeRequest(
        params: LoadRangeParams,
        callback: LoadRangeCallback<T>
    ): Request

    protected abstract fun loadData(request: Request): Observable<R>

    override fun invalidate() {
        loadSubscription.unsubscribe()
        super.invalidate()
    }

    protected inner class Request(
        val limit: Int,
        val position: Int,
        val order: OrderEntity,
        val callback: R.() -> Unit
    )
}
