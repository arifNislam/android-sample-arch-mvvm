package com.binjar.sample.data

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Check if user device is connected to internet
 * @return false if not connected
 */
fun isConnected(appContext: Context): Boolean {
    val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun <T> Single<T>.withScheduler(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.withScheduler(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<Response<T>>.onResponse(): Single<T> {
    return this.map { response ->
        if (response.isSuccessful) {
            response.body()
        } else {
            throw ApiException(response.code(), response.errorBody(), response.message())
        }
    }
}

fun <T> Flowable<Response<T>>.onResponse(): Flowable<T> {
    return this.map { response ->
        if (response.isSuccessful) {
            response.body()
        } else {
            throw ApiException(response.code(), response.errorBody(), response.message())
        }
    }
}

fun <T> Single<T>.onException(appContext: Context): Single<T> {
    return this.onErrorResumeNext { it: Throwable ->
        Single.create<T> { emitter ->
            if (it is ApiException) {
                emitter.onError(ErrorHandler.parseRequestException(appContext, it.code, it.errorBody))
            } else {
                emitter.onError(ErrorHandler.parseIOException(appContext))
            }
        }
    }
}

fun <T> Flowable<T>.onException(appContext: Context): Flowable<T> {
    return this.onErrorResumeNext { it: Throwable ->
        Flowable.create<T>({ emitter ->
            if (it is ApiException) {
                emitter.onError(ErrorHandler.parseRequestException(appContext, it.code, it.errorBody))
            } else {
                emitter.onError(ErrorHandler.parseIOException(appContext))
            }
        }, BackpressureStrategy.BUFFER)
    }
}
