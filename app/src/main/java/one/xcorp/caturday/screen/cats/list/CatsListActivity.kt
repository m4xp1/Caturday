package one.xcorp.caturday.screen.cats.list

import android.os.Bundle
import one.xcorp.caturday.activity.BaseActivity
import one.xcorp.caturday.R

class CatsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
    }
}
