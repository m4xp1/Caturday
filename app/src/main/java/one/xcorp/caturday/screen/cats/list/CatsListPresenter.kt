package one.xcorp.caturday.screen.cats.list

import android.content.Context
import androidx.core.content.ContextCompat.getMainExecutor
import androidx.paging.PagedList
import one.xcorp.caturday.BasePresenterImpl
import one.xcorp.caturday.data.paging.RxPositionalDataSource
import one.xcorp.caturday.screen.cats.list.mapper.toStateModel
import one.xcorp.caturday.screen.cats.list.model.CatModel
import one.xcorp.caturday.screen.cats.list.model.StateModel
import one.xcorp.caturday.screen.cats.list.model.StateModel.Running
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.plusAssign
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import java.util.concurrent.Executors.newSingleThreadExecutor
import javax.inject.Inject
import javax.inject.Provider

class CatsListPresenter @Inject constructor(
    private val applicationContext: Context,
    private val savedInstanceState: CatsListContract.State?,
    private val catsListDataSourceProvider: Provider<RxPositionalDataSource<CatModel>>
) : BasePresenterImpl<CatsListContract.View, CatsListContract.State>(), CatsListContract.Presenter {

    private val catsListStateSubject = PublishSubject.create<StateModel>()
    private val catsListStateObservable = catsListStateSubject
        .startWith(Running(true))
        .distinctUntilChanged()
        .replay(1)

    private val createCatsListSubject = PublishSubject.create<Int>()
    private val createCatsListObservable = createCatsListSubject
        .startWith(getInitialPosition())
        .observeOn(Schedulers.io())
        .map<PagedList<CatModel>> { createCatsList(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext { catsList = it }
        .replay(1)

    private var catsList: PagedList<CatModel>? = null

    init {
        presenterSubscriptions += catsListStateObservable.connect()
        presenterSubscriptions += createCatsListObservable.connect()
    }

    override fun attach(view: CatsListContract.View) {
        super.attach(view)

        viewSubscriptions += catsListStateObservable.subscribe {
            view.showCatsListState(it)
        }

        viewSubscriptions += createCatsListObservable.subscribe {
            view.showCatsList(it)
        }
    }

    override fun getState(): CatsListContract.State {
        return CatsListState(getInitialPosition())
    }

    private fun createCatsList(initialKey: Int = 0, pageSize: Int = 10): PagedList<CatModel> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(pageSize)
            .build()

        return PagedList.Builder<Int, CatModel>(createCatsListDataSource(), config)
            .setInitialKey(initialKey)
            .setFetchExecutor(newSingleThreadExecutor())
            .setNotifyExecutor(getMainExecutor(applicationContext))
            .build()
    }

    private fun createCatsListDataSource(): RxPositionalDataSource<CatModel> {
        val dataSource = catsListDataSourceProvider.get()

        dataSource.observeLoading()
            .map { it.toStateModel() }
            .subscribe { catsListStateSubject.onNext(it) }

        dataSource.addInvalidatedCallback {
            createCatsListSubject.onNext(getInitialPosition())
        }

        return dataSource
    }

    private fun getInitialPosition(): Int =
        catsList?.lastKey as? Int ?: savedInstanceState?.initialPosition ?: 0

    override fun invalidate() {
        catsList?.dataSource?.invalidate()
    }
}
