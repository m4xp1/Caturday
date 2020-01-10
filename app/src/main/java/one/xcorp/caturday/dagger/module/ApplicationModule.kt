package one.xcorp.caturday.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import one.xcorp.caturday.dagger.CatsListComponent
import one.xcorp.caturday.dagger.scope.ApplicationScope

@Module
class ApplicationModule {

    @Provides
    @ApplicationScope
    fun applicationContext(application: Application): Context = application

    @Provides
    @ApplicationScope
    fun catsListComponentHolder(factory: CatsListComponent.Factory): CatsListComponent.Holder =
        CatsListComponent.Holder(factory)
}
