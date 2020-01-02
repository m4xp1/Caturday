package one.xcorp.caturday.data.source.network

import one.xcorp.caturday.data.mapper.toDto
import one.xcorp.caturday.data.mapper.toEntity
import one.xcorp.caturday.data.source.network.CatsApi.Companion.HEADER_PAGINATION_COUNT
import one.xcorp.caturday.data.source.network.dto.SearchParamsDto
import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.PageEntity
import rx.Single
import rx.schedulers.Schedulers
import javax.inject.Inject

internal class CatsNetworkSource @Inject constructor(
    private val catsApi: CatsApi
) {

    fun getCatsImages(
        size: Int,
        position: Int,
        order: OrderEntity
    ): Single<PageEntity<CatImageEntity>> = with(SearchParamsDto(size, position)) {
        return catsApi.searchImages(limit, page, order.toDto())
            .subscribeOn(Schedulers.io())
            .map {
                val paginationCount = it.headers().get(HEADER_PAGINATION_COUNT)

                val totalItems = requireNotNull(paginationCount?.toIntOrNull()) {
                    "$HEADER_PAGINATION_COUNT header does not exist or not a int value."
                }
                val items = it.body()?.drop(offset)?.take(size) ?: emptyList()

                PageEntity(totalItems, items.toEntity())
            }
    }
}
