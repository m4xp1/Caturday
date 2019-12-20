package one.xcorp.caturday.data.source.network

import one.xcorp.caturday.data.source.network.CatsApi.Companion.PAGINATION_COUNT_HEADER
import one.xcorp.caturday.data.source.network.dto.CatImageDto
import one.xcorp.caturday.domain.entity.PageEntity
import retrofit2.Retrofit
import rx.Single
import javax.inject.Inject
import kotlin.math.ceil

class CatsNetworkSource @Inject constructor(
    private val catsApi: CatsApi
) {

    fun getCatsImages(limit: Int, page: Int, order: String): Single<PageEntity<CatImageDto>> =
        catsApi.searchImages(limit, page, order).map {
            val totalItems = requireNotNull(it.headers().get(PAGINATION_COUNT_HEADER)) {
                "$PAGINATION_COUNT_HEADER header does not exist"
            }.toFloat()

            val totalPage = ceil(totalItems / limit).toInt()
            val items = it.body() ?: emptyList()

            PageEntity(page, totalPage, items)
        }
}
