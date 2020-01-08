package one.xcorp.caturday.screen.cats.list

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cats_list.*
import one.xcorp.caturday.Application.Dependencies.applicationComponent
import one.xcorp.caturday.BaseActivity
import one.xcorp.caturday.R
import one.xcorp.caturday.dagger.CatsListComponent
import one.xcorp.caturday.dagger.holder.injectWith
import one.xcorp.caturday.dagger.injector.Injector
import one.xcorp.caturday.screen.cats.list.adapter.CatsListAdapter
import one.xcorp.caturday.screen.cats.list.model.CatModel
import one.xcorp.caturday.screen.cats.list.model.StateModel
import one.xcorp.caturday.screen.cats.list.model.StateModel.*
import javax.inject.Inject

class CatsListActivity : BaseActivity(), CatsListContract.View {

    @Inject
    lateinit var presenter: CatsListContract.Presenter

    private val adapter by lazy { CatsListAdapter() }

    private val injector = Injector<CatsListComponent> { it.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)

        configureRecyclerView()

        presenter.attach(this)
    }

    override fun onInject(savedInstanceState: Bundle?) = applicationComponent
        .catsListComponentHolder
        .injectWith(injector) {
            createComponent(
                savedInstanceState?.getParcelable(KEY_CATS_LIST_CONTRACT_PRESENTER_STATE)
            )
        }

    private fun configureRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)

        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_CATS_LIST_CONTRACT_PRESENTER_STATE, presenter.getState())
    }

    override fun showCatsListState(state: StateModel) = when (state) {
        is Running -> {
            progressView.visibility = if (state.isInitial) VISIBLE else GONE
            recyclerView.visibility = if (state.isInitial) GONE else VISIBLE
        }
        is Failed -> {
            Snackbar.make(recyclerView, state.message, LENGTH_INDEFINITE)
                .setAction(R.string.retry_button) { state.retry() }
                .show()
        }
        is Success -> {
            progressView.visibility = GONE
            recyclerView.visibility = VISIBLE
        }
    }

    override fun showCatsList(list: PagedList<CatModel>) {
        adapter.submitList(list)
    }

    override fun onDestroy() {
        presenter.detach()

        if (isFinishing) {
            presenter.dispose()
        }
        super.onDestroy()
    }

    private companion object {

        private const val KEY_CATS_LIST_CONTRACT_PRESENTER_STATE =
            "CatsListContract.Presenter.State"
    }

    fun testClick(view: View) {
        presenter.invalidate()
    }
}
