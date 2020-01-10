package one.xcorp.caturday.data.dagger.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import one.xcorp.caturday.data.CatsApiConfiguration
import one.xcorp.caturday.data.source.retrofit.CatsRetrofitApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class RetrofitModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): RxJavaCallAdapterFactory =
        RxJavaCallAdapterFactory.create()

    @Provides
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory,
        rxJavaCallAdapterFactory: RxJavaCallAdapterFactory
    ): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJavaCallAdapterFactory)

    @Provides
    @Singleton
    fun provideCatsRetrofitApi(
        configuration: CatsApiConfiguration,
        httpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): CatsRetrofitApi {
        httpClientBuilder.addInterceptor {
            val request: Request = it.request().newBuilder()
                .header(CatsRetrofitApi.HEADER_X_API_KEY, configuration.key)
                .build()
            it.proceed(request)
        }

        retrofitBuilder.baseUrl(configuration.url)
        retrofitBuilder.client(httpClientBuilder.build())

        return retrofitBuilder.build().create(CatsRetrofitApi::class.java)
    }
}
