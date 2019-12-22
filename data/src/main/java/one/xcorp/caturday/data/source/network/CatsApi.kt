package one.xcorp.caturday.data.source.network

import one.xcorp.caturday.data.source.network.dto.CatImageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

internal interface CatsApi {

    @GET(IMAGES_SEARCH_METHOD)
    fun searchImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: String
    ): Single<Response<List<CatImageDto>>>

    companion object {

        const val CATS_API_BASE_URL = "https://api.thecatapi.com/v1/"

        const val X_API_KEY_HEADER = "x-api-key"
        const val PAGINATION_COUNT_HEADER = "pagination-count"

        private const val IMAGES_SEARCH_METHOD = "images/search"
    }
}
