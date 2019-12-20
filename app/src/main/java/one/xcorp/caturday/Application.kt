package one.xcorp.caturday

import android.app.Application
import one.xcorp.caturday.data.di.DaggerDataComponent
import one.xcorp.caturday.di.DaggerApplicationComponent

class Application : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            DaggerDataComponent.factory().create(BuildConfig.CATS_API_KEY)
        )
    }
}
