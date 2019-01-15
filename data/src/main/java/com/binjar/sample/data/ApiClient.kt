package com.binjar.sample.data

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val TIME_OUT = 30L

    var cacheSize: Long = 10 * 1024 * 1024 // 10 MB

    fun <Service> createService(context: Context, serviceClass: Class<Service>, baseUrl: String = BASE_URL, timeOut: Long = TIME_OUT): Service {
        return createRetrofit(context, baseUrl, timeOut).create(serviceClass)
    }

    /**
     * @return new retrofit instance with the provided base url and the converter factories
     */
    fun createRetrofit(context: Context, baseUrl: String = BASE_URL, timeOut: Long = TIME_OUT): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient(context, timeOut))
                .build()
    }

    /**
     * Creates OkHttpClient and add interceptors
     */
    private fun createOkHttpClient(context: Context, timeOut: Long = TIME_OUT): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpBuilder.addInterceptor(loggingInterceptor)
        }

        return okHttpBuilder/*.cache(Cache(context.cacheDir, cacheSize))*/.build()  // todo enable caching later
    }
}
