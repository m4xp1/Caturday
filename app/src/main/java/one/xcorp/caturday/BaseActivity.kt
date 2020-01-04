package one.xcorp.caturday

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun getContext(): Context = this
}
