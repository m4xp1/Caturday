package one.xcorp.caturday.domain.repository

import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.PageEntity
import rx.Single

interface CatsRepository {

    fun getCatsImages(
        limit: Int,
        position: Int,
        order: OrderEntity
    ): Single<PageEntity<CatImageEntity>>
}
