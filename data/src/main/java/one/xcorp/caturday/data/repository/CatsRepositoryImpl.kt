package one.xcorp.caturday.data.repository

import one.xcorp.caturday.data.source.network.CatsNetworkSource
import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.repository.CatsRepository
import rx.Single
import javax.inject.Inject

internal class CatsRepositoryImpl @Inject constructor(
    private val catsNetworkSource: CatsNetworkSource
) : CatsRepository {

    override fun getCatsImages(
        size: Int,
        position: Int,
        order: OrderEntity
    ): Single<PageEntity<CatImageEntity>> {
        return catsNetworkSource.getCatsImages(size, position, order)
    }
}
