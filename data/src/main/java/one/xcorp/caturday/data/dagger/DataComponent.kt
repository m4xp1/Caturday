package one.xcorp.caturday.data.dagger

import dagger.BindsInstance
import dagger.Component
import one.xcorp.caturday.data.dagger.module.ApiModule
import one.xcorp.caturday.data.dagger.module.NetworkModule
import one.xcorp.caturday.data.dagger.module.RepositoryModule
import one.xcorp.caturday.data.dagger.qualifier.CatsApiKey
import one.xcorp.caturday.domain.repository.CatsRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ApiModule::class,
        RepositoryModule::class
    ]
)
interface DataComponent {

    val catsRepository: CatsRepository

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance @CatsApiKey catsApiKey: String): DataComponent
    }
}
