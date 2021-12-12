package ru.ksart.tmdb_movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.ksart.tmdb_movies.data.network.ApiKeyInterceptor
import ru.ksart.tmdb_movies.data.network.TmdbApiService
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @LoggingInterceptorForOkHttpClient
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @ApiKeyInterceptorForOkHttpClient
    @Provides
    fun provideApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptorForOkHttpClient loggingInterceptor: Interceptor,
        @ApiKeyInterceptorForOkHttpClient apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(apiKeyInterceptor)
            .retryOnConnectionFailure(false) // not necessary but useful!
            .build()
    }

    @Provides
    fun providerConverter(): Converter.Factory = MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TmdbApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbService(retrofit: Retrofit): TmdbApiService = retrofit.create()

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptorForOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInterceptorForOkHttpClient
