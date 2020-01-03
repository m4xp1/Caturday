package one.xcorp.caturday.data.dagger.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import one.xcorp.caturday.data.dagger.qualifier.CatsApiKey
import one.xcorp.caturday.data.source.retrofit.CatsRetrofitApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class ApiModule {

    @Provides
    @Singleton
    fun provideCatsRetrofitApi(
        @CatsApiKey apiKey: String,
        httpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): CatsRetrofitApi {
        httpClientBuilder.addInterceptor {
            val request: Request = it.request().newBuilder()
                .header(CatsRetrofitApi.HEADER_X_API_KEY, apiKey)
                .build()
            it.proceed(request)
        }

        retrofitBuilder.baseUrl(CatsRetrofitApi.URL_BASE)
        retrofitBuilder.client(httpClientBuilder.build())

        return retrofitBuilder.build().create(CatsRetrofitApi::class.java)
    }
}
