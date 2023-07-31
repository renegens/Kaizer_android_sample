package eu.giant.kaizen.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.giant.kaizen.data.network.SportAPI
import eu.giant.kaizen.data.network.SportRemoteDataSource
import eu.giant.kaizer.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @AuthorizationOkHttpClient
    @Provides
    @Singleton
    fun provideAuthorizedOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @AuthorizationOkHttpClient okHttpClient: OkHttpClient, gson: Gson
    ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_BASE_ENDPOINT).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()

    @Singleton
    @Provides
    fun provideSportAPI(retrofit: Retrofit): SportAPI = retrofit.create(SportAPI::class.java)

    @Provides
    @Singleton
    fun provideSportRemoteDataSource(
        sportApi: SportAPI, @IoDispatcher dispatcher: CoroutineDispatcher
    ): SportRemoteDataSource = SportRemoteDataSource(
        sportAPI = sportApi, ioDispatcher = dispatcher
    )

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthorizationOkHttpClient
