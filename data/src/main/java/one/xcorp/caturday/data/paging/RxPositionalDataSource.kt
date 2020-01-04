package one.xcorp.caturday.data.paging

import androidx.paging.PositionalDataSource
import one.xcorp.caturday.data.paging.State.*
import rx.Observable
import rx.Observable.just
import rx.Single
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.addTo
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
import rx.subscriptions.CompositeSubscription

abstract class RxPositionalDataSource<T, R>(
    compositeSubscription: CompositeSubscription
) : PositionalDataSource<T>() {

    private val subscriptionSubject = ReplaySubject.create<Unit>()

    private val loadSubject = PublishSubject.create<Request>()
    private val loadObservable = loadSubject
        .concatMap { request ->
            just<State<Info, R>>(Running(request.toInfo())).concatWith(
                loadDataSingle(request).toObservable()
                    .map<State<Info, R>> { Success(it) }
                    .onErrorReturn {
                        Failed(it) {
                            loadSubject.onNext(request)
                        }
                    }
            )
        }
        .doOnUnsubscribe { subscriptionSubject.onNext(Unit) }
        .publish()

    private val loadSubscription: Subscription

    init {
        loadSubscription = loadObservable.connect()
            .addTo(compositeSubscription)
    }

    fun observeState(): Observable<State<Info, R>> = loadObservable
        .takeUntil(subscriptionSubject)
        .observeOn(AndroidSchedulers.mainThread())

    protected fun loadData(request: Request, callback: R.() -> Unit) {
        loadObservable.doOnSubscribe { loadSubject.onNext(request) }
            .takeUntil(subscriptionSubject)
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
