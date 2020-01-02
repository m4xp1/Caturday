package one.xcorp.caturday.data.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import one.xcorp.caturday.data.di.qualifier.CatsApiKey
import one.xcorp.caturday.data.source.network.CatsApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class ApiModule {

    @Provides
    @Singleton
    fun provideCatsApi(
        @CatsApiKey apiKey: String,
        httpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): CatsApi {
        httpClientBuilder.addInterceptor {
            val request: Request = it.request().newBuilder()
                .header(CatsApi.HEADER_X_API_KEY, apiKey)
                .build()
            it.proceed(request)
        }

        retrofitBuilder.baseUrl(CatsApi.URL_BASE)
        retrofitBuilder.client(httpClientBuilder.build())

        return retrofitBuilder.build().create(CatsApi::class.java)
    }
}
