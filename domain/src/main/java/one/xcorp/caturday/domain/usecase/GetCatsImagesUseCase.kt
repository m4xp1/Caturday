package one.xcorp.caturday.domain.usecase

import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.repository.CatsRepository
import rx.Single
import javax.inject.Inject

class GetCatsImagesUseCase @Inject constructor(
    private val catsRepository: CatsRepository
) {

    operator fun invoke(
        limit: Int,
        position: Int,
        order: OrderEntity
    ): Single<PageEntity<CatImageEntity>> = catsRepository.getCatsImages(limit, position, order)
}
