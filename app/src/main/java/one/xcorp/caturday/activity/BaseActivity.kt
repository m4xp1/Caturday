package one.xcorp.caturday.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import one.xcorp.caturday.view.BaseView

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun getContext(): Context = this
}
