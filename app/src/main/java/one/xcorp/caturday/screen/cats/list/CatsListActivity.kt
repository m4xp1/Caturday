package one.xcorp.caturday.screen.cats.list

import android.os.Bundle
import one.xcorp.caturday.Application.Dependencies.graph
import one.xcorp.caturday.Application.Dependencies.obtain
import one.xcorp.caturday.Application.Dependencies.release
import one.xcorp.caturday.R
import one.xcorp.caturday.activity.BaseActivity
import one.xcorp.caturday.model.CatModel
import javax.inject.Inject

class CatsListActivity : BaseActivity(), CatsListContract.View {

    @Inject
    lateinit var presenter: CatsListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        obtain(graph::catsList).inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cats_list)
        presenter.attach(this)
    }

    override fun showError(throwable: Throwable) {

    }

    override fun showCats(items: List<CatModel>) {

    }

    override fun onDestroy() {
        presenter.detach()

        if (isFinishing) {
            release(graph::catsList)
        }
        super.onDestroy()
    }
}
