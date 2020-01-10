package one.xcorp.caturday.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import one.xcorp.caturday.dagger.module.ApplicationModule
import one.xcorp.caturday.data.dagger.DataComponent
import one.xcorp.caturday.dagger.scope.ApplicationScope

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

    val catsListComponentHolder: CatsListComponent.Holder

    val catsListComponentFactory: CatsListComponent.Factory

    @Component.Factory
    interface Factory {

        fun createComponent(
            @BindsInstance application: Application,
            dataComponent: DataComponent
        ): ApplicationComponent
    }
}
