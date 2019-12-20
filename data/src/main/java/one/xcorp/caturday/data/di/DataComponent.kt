package one.xcorp.caturday.data.di

import dagger.BindsInstance
import dagger.Component
import one.xcorp.caturday.data.di.module.ApiModule
import one.xcorp.caturday.data.di.module.NetworkModule
import one.xcorp.caturday.data.di.module.RepositoryModule
import one.xcorp.caturday.data.di.qualifier.CatsApiKey
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

    fun catsRepository(): CatsRepository

    interface Factory {

        fun create(@BindsInstance @CatsApiKey catsApiKey: String): DataComponent
    }
}
