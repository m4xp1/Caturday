package one.xcorp.caturday.screen.cats.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.updatePadding
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cats_list.*
import one.xcorp.caturday.Application.Dependencies.applicationComponent
import one.xcorp.caturday.BaseActivity
import one.xcorp.caturday.R
import one.xcorp.caturday.dagger.CatsListComponent
import one.xcorp.didy.holder.injectWith
import one.xcorp.didy.injector.Injector
import one.xcorp.caturday.screen.cats.list.adapter.CatsListAdapter
import one.xcorp.caturday.screen.cats.list.model.CatModel
import one.xcorp.caturday.screen.cats.list.model.StateModel
import one.xcorp.caturday.screen.cats.list.model.StateModel.Failed
import one.xcorp.caturday.screen.cats.list.model.StateModel.Running
import javax.inject.Inject

class CatsListActivity : BaseActivity(), CatsListContract.View {

    @Inject
    lateinit var presenter: CatsListContract.Presenter

    private val adapter by lazy { CatsListAdapter() }

    private val injector = Injector<CatsListComponent> { it.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
        setSupportActionBar(R.id.toolbarView)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_cats_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.refreshAction -> {
            presenter.invalidate(); true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun configureRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)

        recyclerView.layoutManager = GridLayoutManager(this, spanCount)

        recyclerView.setOnApplyWindowInsetsListener { _, insets ->
            insets?.apply { recyclerView.updatePadding(bottom = systemWindowInsetBottom) }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_CATS_LIST_CONTRACT_PRESENTER_STATE, presenter.getState())
    }

    override fun showCatsListState(state: StateModel) {
        progressView.visibility = GONE
        recyclerView.visibility = VISIBLE

        when {
            state is Running && state.isInitial -> {
                progressView.visibility = VISIBLE
                recyclerView.visibility = GONE
            }
            state is Failed -> {
                Snackbar.make(recyclerView, state.message, LENGTH_INDEFINITE)
                    .setAction(R.string.retry_button) { state.retry() }
                    .show()
            }
        }
    }

    override fun showCatsList(list: PagedList<CatModel>) {
        adapter.submitList(list)

        if (recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }
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
}
