package one.xcorp.caturday.datasource

import androidx.paging.PositionalDataSource
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.model.StatusModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject

abstract class RxPositionalDataSource<T, R> : PositionalDataSource<T>() {

    private val statusSubject = PublishSubject.create<StatusModel>()
    private val statusObservable = statusSubject.asObservable()
        .observeOn(AndroidSchedulers.mainThread())

    private val loadSubject = PublishSubject.create<Request>()
    private val loadObservable = loadSubject.asObservable()
        .subscribeOn(Schedulers.io())
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

    fun isDisposed(): Boolean = loadObservable.isUnsubscribed

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

    fun dispose() = loadObservable.unsubscribe()

    protected inner class Request(
        val limit: Int,
        val position: Int,
        val order: OrderEntity,
        val callback: R.() -> Unit
    )
}
