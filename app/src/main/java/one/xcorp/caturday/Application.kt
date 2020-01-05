package one.xcorp.caturday

import one.xcorp.caturday.dagger.ApplicationComponent
import one.xcorp.caturday.dagger.DaggerApplicationComponent
import one.xcorp.caturday.data.dagger.DaggerDataComponent

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
        fun <T : Any> obtain(supplier: () -> T): T {
            return holder.getOrPut(supplier, supplier) as T
        }

        fun <T> release(supplier: () -> T): Boolean {
            return holder.remove(supplier) != null
        }
    }
}
