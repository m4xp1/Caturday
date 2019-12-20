package one.xcorp.caturday.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import one.xcorp.caturday.R

class CatsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats_list)
    }
}
