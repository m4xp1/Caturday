package one.xcorp.caturday.data.source.network

import one.xcorp.caturday.data.source.network.CatsApi.Companion.PAGINATION_COUNT_HEADER
import one.xcorp.caturday.data.source.network.dto.CatImageDto
import one.xcorp.caturday.domain.entity.PageEntity
import rx.Single
import rx.schedulers.Schedulers
import javax.inject.Inject

internal class CatsNetworkSource @Inject constructor(
    private val catsApi: CatsApi
) {

    fun getCatsImages(limit: Int, position: Int, order: String): Single<PageEntity<CatImageDto>> =
        catsApi.searchImages(limit, position / limit, order)
            .subscribeOn(Schedulers.io())
            .map {
                val totalItems = requireNotNull(it.headers().get(PAGINATION_COUNT_HEADER)) {
                    "$PAGINATION_COUNT_HEADER header does not exist"
                }.toInt()
                val items = it.body() ?: emptyList()

                PageEntity(position, totalItems, items)
            }
}
