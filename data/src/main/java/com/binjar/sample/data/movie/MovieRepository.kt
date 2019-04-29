package com.binjar.sample.data.movie

import android.app.Application
import com.binjar.sample.data.movie.model.MovieResponse
import com.binjar.sample.data.movie.model.SortBy
import com.binjar.sample.data.movie.network.MovieApi
import com.binjar.sample.data.onException
import io.reactivex.Flowable


class MovieRepository private constructor(
        private val app: Application,
        private val api: MovieApi
) : MovieDataSource {

    override fun discoverMovies(page: Int, sortBy: SortBy): Flowable<MovieResponse> {
        return api.discoverMovies(page, sortBy).onException(app)
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(app: Application, api: MovieApi) = instance
                ?: synchronized(this) {
                    instance ?: MovieRepository(app, api).also { instance = it }
                }
    }
}
