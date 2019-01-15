package com.binjar.sample.data.repositories.movie

import android.app.Application
import com.binjar.sample.data.ApiClient
import com.binjar.sample.data.onResponse
import com.binjar.sample.data.repositories.movie.model.MovieResponse
import io.reactivex.Flowable


class MovieApi private constructor() {

    private lateinit var service: MovieService

    fun discoverMovies(queryUntil: String, page: Int): Flowable<MovieResponse> {
        return service.discover(queryUntil, page).onResponse()
    }

    companion object {
        @Volatile
        private var instance: MovieApi? = null

        fun getInstance(application: Application) = instance
                ?: synchronized(this) {
                    instance
                            ?: MovieApi().also {
                                instance = it
                                it.service = ApiClient.createService(application, MovieService::class.java)
                            }
                }
    }
}
