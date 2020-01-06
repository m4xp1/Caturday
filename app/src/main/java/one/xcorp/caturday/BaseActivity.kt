package one.xcorp.caturday

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import one.xcorp.caturday.dagger.holder.ComponentHolder

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var componentHolder: ComponentHolder<*, *>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        componentHolder = onInject(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    open fun onInject(savedInstanceState: Bundle?): ComponentHolder<*, *>? {
        return null
    }

    override fun getContext(): Context = this

    override fun onDestroy() {
        if (isFinishing) {
            componentHolder?.release()
        }
        super.onDestroy()
    }
}
