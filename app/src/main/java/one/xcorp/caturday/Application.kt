package one.xcorp.caturday

import one.xcorp.caturday.BuildConfig.CATS_API_KEY
import one.xcorp.caturday.BuildConfig.CATS_API_URL
import one.xcorp.caturday.dagger.ApplicationComponent
import one.xcorp.caturday.dagger.DaggerApplicationComponent
import one.xcorp.caturday.data.CatsApiConfiguration
import one.xcorp.caturday.data.dagger.DaggerDataComponent

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        initializeGraph(this)
    }

    companion object Dependencies {

        val applicationComponent: ApplicationComponent by lazy {
            DaggerApplicationComponent.factory().createComponent(
                application,
                createDataComponent()
            )
        }

        private lateinit var application: Application

        private fun initializeGraph(application: Application) {
            this.application = application
        }

        private fun createDataComponent() = DaggerDataComponent.factory().createComponent(
            CatsApiConfiguration(CATS_API_KEY, CATS_API_URL)
        )
    }
}
