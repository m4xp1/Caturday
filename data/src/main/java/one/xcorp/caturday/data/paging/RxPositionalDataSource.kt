package one.xcorp.caturday.data.paging

import androidx.paging.PositionalDataSource
import one.xcorp.caturday.data.paging.RxPositionalDataSource.State.*
import rx.Observable
import rx.Single
import rx.Single.just
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject

abstract class RxPositionalDataSource<T> : PositionalDataSource<T>() {

    private val unsubscribeSubject = BehaviorSubject.create<Unit>()

    private val loadSubject = PublishSubject.create<Running<T>>()
    private val loadObservable = loadSubject
        .concatMap<State<T>> { running ->
            just<State<T>>(running).concatWith(
                loadData(running.size, running.position, running.isInitial)
                    .map<State<T>> { it }
                    .onErrorReturn {
                        Failed(it) {
                            loadSubject.onNext(running)
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

    fun observeLoading(): Observable<State<T>> = loadObservable
        .takeUntil(unsubscribeSubject)
        .observeOn(AndroidSchedulers.mainThread())

    final override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<T>
    ) {
        loadData(params.requestedLoadSize, params.requestedStartPosition, true) {
            if (totalCount == null) {
                callback.onResult(items, position)
            } else {
                callback.onResult(items, position, totalCount)
            }
        }
    }

    final override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        loadData(params.loadSize, params.startPosition, false) {
            callback.onResult(items)
        }
    }

    private fun loadData(
        size: Int,
        position: Int,
        isInitial: Boolean,
        callback: Success<T>.() -> Unit
    ) {
        loadObservable
            .doOnSubscribe { loadSubject.onNext(Running(size, position, isInitial)) }
            .takeUntil(unsubscribeSubject)
            .takeFirst { it is Success }
            .map { it as Success<T> }
            .toBlocking()
            .subscribe(callback)
    }

    protected abstract fun loadData(
        size: Int,
        position: Int,
        isInitial: Boolean
    ): Single<Success<T>>

    override fun invalidate() {
        loadSubscription.unsubscribe()
        super.invalidate()
    }

    sealed class State<T> {

        data class Running<T>(
            val size: Int,
            val position: Int,
            val isInitial: Boolean
        ) : State<T>()

        data class Failed<T>(
            val error: Throwable,
            val retry: () -> Unit
        ) : State<T>()

        data class Success<T>(
            val items: List<T>,
            val position: Int,
            val totalCount: Int?
        ) : State<T>()
    }
}
