package one.xcorp.caturday.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import ru.cardsmobile.mw3.barch.presentation.di.scope.ApplicationScope

@Module
abstract class ApplicationModule {

    @Binds
    @ApplicationScope
    abstract fun bindApplicationContext(application: Application): Context
}
