package one.xcorp.caturday.data.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): RxJavaCallAdapterFactory =
        RxJavaCallAdapterFactory.create()

    @Provides
    fun provideHttpClientBuilder() = OkHttpClient.Builder()

    @Provides
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory,
        rxJavaCallAdapterFactory: RxJavaCallAdapterFactory
    ): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
}
