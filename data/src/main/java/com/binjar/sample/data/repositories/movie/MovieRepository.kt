package com.binjar.sample.data.repositories.movie

import android.app.Application
import com.binjar.sample.data.onException
import com.binjar.sample.data.repositories.movie.model.Movie
import io.reactivex.Flowable
import java.util.*


class MovieRepository private constructor(private val application: Application, private val remoteSource: MovieDataSource) : MovieDataSource {

    override fun discoverMovies(until: String): Flowable<ArrayList<Movie>> {
        return remoteSource.discoverMovies(until)
                .onException(application)
                .map { result ->
                    return@map result
                }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(application: Application, remoteSource: MovieDataSource) = instance
                ?: synchronized(this) {
                    instance
                            ?: MovieRepository(application, remoteSource).also { instance = it }
                }
    }
}
