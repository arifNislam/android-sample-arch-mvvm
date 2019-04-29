package com.binjar.sample.data.movie.network

import android.app.Application
import com.binjar.sample.data.ApiClient
import com.binjar.sample.data.movie.model.MovieResponse
import com.binjar.sample.data.movie.model.SortBy
import com.binjar.sample.data.onResponse
import io.reactivex.Flowable


class MovieApi private constructor() : MovieNetworkSource {

    private lateinit var service: MovieService

    override fun discoverMovies(page: Int, sortBy: SortBy): Flowable<MovieResponse> {
        val queries = HashMap<String, String>()
        queries["page"] = page.toString()
        queries["sort_by"] = "${sortBy.value}.${sortBy.order}"
        return service.discover(queries).onResponse()
    }

    fun fetchUpcomingMovies(page: Int): Flowable<MovieResponse> {
        return service.fetchUpcomingMovies(page).onResponse()
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
