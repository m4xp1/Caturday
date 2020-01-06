package one.xcorp.caturday.data.dagger.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
}
