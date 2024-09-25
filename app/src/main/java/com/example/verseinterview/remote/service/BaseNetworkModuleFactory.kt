package com.example.verseinterview.remote.service

import Keys.url
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Named

open class BaseNetworkModuleFactory {
    companion object {
        val CONNECT_TIMEOUT by lazy {
            30L
        }
        val READ_TIMEOUT by lazy {
            30L
        }
        val WRITE_TIMEOUT by lazy {
            30L
        }
        val SUCCESS_RESPONSE_CODE by lazy {
            200
        }
    }

    /**
     * Provides [OkHttpClient] instance
     */
    fun makeOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(provideLogInterceptor())
        .addInterceptor(provideRequestInterceptor())
        .build()

    /**
     * Provides [HttpLoggingInterceptor] instance
     */
    @Named(NetworkModuleFactory.LOG_INTERCEPTOR)
    internal fun provideLogInterceptor(): Interceptor {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return logInterceptor
    }

    @Named(NetworkModuleFactory.REQUEST_INTERCEPTOR)
    internal fun provideRequestInterceptor(): Interceptor = RequestInterceptor()

    /**
     * Provides [Retrofit] instance
     */
    fun buildRetrofitObject(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .callbackExecutor(Executors.newSingleThreadExecutor())
        .build()
}
