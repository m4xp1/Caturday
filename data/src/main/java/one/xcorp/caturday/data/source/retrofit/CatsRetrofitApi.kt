package one.xcorp.caturday.data.source.retrofit

import one.xcorp.caturday.data.source.retrofit.dto.CatImageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

internal interface CatsRetrofitApi {

    @GET(METHOD_IMAGES_SEARCH)
    fun searchImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: String
    ): Single<Response<List<CatImageDto>>>

    companion object {

        const val URL_BASE = "https://api.thecatapi.com/v1/"

        const val HEADER_X_API_KEY = "x-api-key"
        const val HEADER_PAGINATION_COUNT = "pagination-count"

        private const val METHOD_IMAGES_SEARCH = "images/search"
    }
}
