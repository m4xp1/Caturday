package one.xcorp.caturday.data.paging

import androidx.paging.PositionalDataSource
import one.xcorp.caturday.domain.entity.StateEntity
import one.xcorp.caturday.domain.entity.StateEntity.*
import rx.Observable
import rx.Observable.just
import rx.Single
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject

abstract class RxPositionalDataSource<T, R> : PositionalDataSource<T>() {

    private val unsubscribeSubject = ReplaySubject.create<Unit>()

    private val loadSubject = PublishSubject.create<Request>()
    private val loadObservable = loadSubject
        .concatMap { request ->
            just<StateEntity<Info, R>>(Running(request.toInfo())).concatWith(
                loadDataSingle(request).toObservable()
                    .map<StateEntity<Info, R>> { Success(it) }
                    .onErrorReturn {
                        Failed(it) {
                            loadSubject.onNext(request)
                        }
                    }
            )
        }
        .doOnUnsubscribe { unsubscribeSubject.onNext(Unit) }
        .publish()

    private val loadSubscription: Subscription

    init {
        loadSubscription = loadObservable.connect()
    }

    fun observeState(): Observable<StateEntity<Info, R>> = loadObservable
        .takeUntil(unsubscribeSubject)
        .observeOn(AndroidSchedulers.mainThread())

    protected fun loadData(request: Request, callback: R.() -> Unit) {
        loadObservable.doOnSubscribe { loadSubject.onNext(request) }
            .takeUntil(unsubscribeSubject)
            .takeFirst { it is Success }
            .map { it as Success<R> }
            .toBlocking()
            .subscribe {
                callback(it.result)
            }
    }

    protected abstract fun loadDataSingle(request: Request): Single<R>

    override fun invalidate() {
        loadSubscription.unsubscribe()
        super.invalidate()
    }

    data class Info(
        val isInitial: Boolean
    )

    protected inner class Request(
        val size: Int,
        val position: Int,
        val isInitial: Boolean
    )

    private fun Request.toInfo() = Info(isInitial)
}
