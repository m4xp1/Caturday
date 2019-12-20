package one.xcorp.caturday.data.repository

import one.xcorp.caturday.data.mapper.toEntity
import one.xcorp.caturday.data.source.network.CatsNetworkSource
import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.repository.CatsRepository
import rx.Single
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val catsNetworkSource: CatsNetworkSource
) : CatsRepository {

    override fun getCatsImages(
        limit: Int,
        page: Int,
        order: String
    ): Single<PageEntity<CatImageEntity>> {
        return catsNetworkSource.getCatsImages(limit, page, order)
            .map { PageEntity(it.current, it.total, it.items.toEntity()) }
    }
}
