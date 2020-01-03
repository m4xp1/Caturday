package one.xcorp.caturday.data.source.retrofit

import one.xcorp.caturday.data.mapper.toDto
import one.xcorp.caturday.data.mapper.toEntity
import one.xcorp.caturday.data.source.retrofit.CatsRetrofitApi.Companion.HEADER_PAGINATION_COUNT
import one.xcorp.caturday.data.source.retrofit.dto.SearchParamsDto
import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.PageEntity
import rx.Single
import rx.schedulers.Schedulers
import javax.inject.Inject

internal class CatsRetrofitSource @Inject constructor(
    private val catsApi: CatsRetrofitApi
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

                PageEntity(position, totalItems, items.toEntity())
            }
    }
}
