package com.binjar.sample.data

import android.app.Application
import com.binjar.sample.data.movie.network.MovieApi
import com.binjar.sample.data.movie.MovieDataSource
import com.binjar.sample.data.movie.MovieRepository


object Injector {
    fun provideMovieRepository(application: Application): MovieDataSource {
        return MovieRepository.getInstance(MovieApi.getInstance(application))
    }
}
