package one.xcorp.caturday.data.di.module

import dagger.Binds
import dagger.Module
import one.xcorp.caturday.data.repository.CatsRepositoryImpl
import one.xcorp.caturday.domain.repository.CatsRepository
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository
}
