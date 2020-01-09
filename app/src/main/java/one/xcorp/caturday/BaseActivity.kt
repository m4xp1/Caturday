package one.xcorp.caturday

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import one.xcorp.caturday.dagger.holder.ComponentHolder

abstract class BaseActivity : AppCompatActivity(), BaseView {

    var toolBar: Toolbar? = null
        private set

    private var componentHolder: ComponentHolder<*, *>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        componentHolder = onInject(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    fun setSupportActionBar(@IdRes id: Int = 0, block: (ActionBar.(Toolbar) -> Unit)? = null) {
        toolBar = findViewById<Toolbar?>(id)
        setSupportActionBar(toolBar)

        toolBar?.let { toolBar ->
            val actionBar = supportActionBar
            if (block != null && actionBar != null) {
                block(actionBar, toolBar)
            }
        }
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
