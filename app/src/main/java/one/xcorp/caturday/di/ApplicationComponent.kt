package one.xcorp.caturday.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import one.xcorp.caturday.data.di.DataComponent
import one.xcorp.caturday.di.module.ApplicationModule
import ru.cardsmobile.mw3.barch.presentation.di.scope.ApplicationScope

@ApplicationScope
@Component(
    dependencies = [
        DataComponent::class
    ],
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    fun catsList(): CatsListComponent

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            dataComponent: DataComponent
        ): ApplicationComponent
    }
}
