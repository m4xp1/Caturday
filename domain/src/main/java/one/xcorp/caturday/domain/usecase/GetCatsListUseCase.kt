package one.xcorp.caturday.domain.usecase

import one.xcorp.caturday.domain.entity.CatEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.PageEntity
import one.xcorp.caturday.domain.repository.CatsRepository
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

@Singleton
class GetCatsListUseCase @Inject constructor(
    private val catsRepository: CatsRepository
) {

    operator fun invoke(
        size: Int,
        position: Int,
        order: OrderEntity,
        sticky: Boolean
    ): Single<PageEntity<CatEntity>> {
        var request = catsRepository.getCatsImages(size, position, order)

        if (sticky && position != 0) {
            request = request.flatMap {
                if (it.items.size == size) {
                    Single.just(it)
                } else {
                    val extremePosition = max(0, it.totalCount - size)
                    catsRepository.getCatsImages(size, extremePosition, order)
                }
            }
        }

        return request.map { page ->
            val items = page.items.map { CatEntity(it.id, it.image) }
            PageEntity(items, page.position, page.totalCount)
        }
    }
}
