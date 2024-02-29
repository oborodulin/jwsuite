package com.oborodulin.jwsuite.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.oborodulin.jwsuite.data.BuildConfig
import com.oborodulin.jwsuite.data.util.Constants.BASE_URL_OVERPASS_API
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by o.borodulin 10.June.2023
 */
// https://medium.com/@jurajkunier/kotlinx-json-vs-gson-4ba24a21bd73
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val chainInterceptor = { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build()
            )
        }

        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(chainInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(ChuckerInterceptor(context))
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(chainInterceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        }
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        // https://github.com/westnordost/osmapi-overpass
        .baseUrl(BASE_URL_OVERPASS_API)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /*
        @Singleton
        @Provides
        fun provideAuthInterceptor(): Interceptor {
            return Interceptor { chain: Interceptor.Chain ->
                val initialRequest = chain.request()

                val newUrl = initialRequest.url.newBuilder()
                    //.addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                    .build()

                val newRequest = initialRequest.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
        }

        @Singleton
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            // Retrofit completely relies on OkHttp for any network operation.
            // Since logging isn’t integrated by default anymore in Retrofit 2,
            // we'll use a logging interceptor for OkHttp.
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            authInterceptor: Interceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideDisneyService(retrofit: Retrofit): NextflixService {
            return retrofit.create(NextflixService::class.java)
        }

     */
}
