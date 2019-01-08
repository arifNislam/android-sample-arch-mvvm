package com.binjar.sample.data.repositories.movie

import android.app.Application
import com.binjar.sample.data.ApiClient
import com.binjar.sample.data.onResponse
import com.binjar.sample.data.repositories.movie.model.Movie
import io.reactivex.Flowable


class MovieApi private constructor() : MovieDataSource {

    private lateinit var service: MovieService

    override fun discoverMovies(until: String): Flowable<ArrayList<Movie>> {
        return service.discover(until)
                .onResponse()
                .map { response ->
                    if (response.movies.isNotEmpty()) response.movies else ArrayList()
                }
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
