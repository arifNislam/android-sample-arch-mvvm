package com.binjar.sample.data

import android.app.Application
import com.binjar.sample.data.movie.MovieApi
import com.binjar.sample.data.movie.MovieRepository
import com.binjar.sample.data.movie.MovieRepositoryImpl


object Injector {
    fun provideMovieRepository(application: Application): MovieRepository {
        return MovieRepositoryImpl.getInstance(MovieApi.getInstance(application))
    }
}
