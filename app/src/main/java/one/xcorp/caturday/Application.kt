package one.xcorp.caturday

import one.xcorp.caturday.data.di.DaggerDataComponent
import one.xcorp.caturday.di.ApplicationComponent
import one.xcorp.caturday.di.DaggerApplicationComponent

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        initializeGraph(this)
    }

    companion object Dependencies {

        val graph: ApplicationComponent by lazy {
            DaggerApplicationComponent.factory().create(
                application,
                DaggerDataComponent.factory().create(BuildConfig.CATS_API_KEY)
            )
        }

        private lateinit var application: Application
        private val holder = mutableMapOf<Any, Any>()

        private fun initializeGraph(application: Application) {
            this.application = application
        }

        @Suppress("UNCHECKED_CAST")
        fun <T : Any> obtain(block: () -> T): T {
            return holder.getOrPut(block, block) as T
        }

        fun <T> release(block: () -> T): Boolean {
            return holder.remove(block) != null
        }
    }
}
