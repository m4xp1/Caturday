package one.xcorp.caturday.data.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import one.xcorp.caturday.data.source.network.CatsApi
import one.xcorp.caturday.data.source.network.CatsApi.Companion.CATS_API_BASE_URL
import one.xcorp.caturday.data.source.network.CatsApi.Companion.X_API_KEY_HEADER
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(
    private val catsApiKey: String
) {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    fun provideHttpClientBuilder() = OkHttpClient.Builder()

    @Provides
    fun provideRetrofitBuilder(gson: Gson) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

    @Provides
    @Singleton
    fun provideCatsApi(
        httpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): CatsApi {
        httpClientBuilder.addInterceptor {
            val request: Request = it.request().newBuilder()
                .header(X_API_KEY_HEADER, catsApiKey)
                .build()
            it.proceed(request)
        }

        retrofitBuilder.baseUrl(CATS_API_BASE_URL)
        retrofitBuilder.client(httpClientBuilder.build())

        return retrofitBuilder.build().create(CatsApi::class.java)
    }
}
