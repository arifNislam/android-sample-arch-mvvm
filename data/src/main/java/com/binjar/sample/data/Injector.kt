package com.binjar.sample.data

import android.app.Application
import com.binjar.sample.data.repositories.movie.MovieApi
import com.binjar.sample.data.repositories.movie.MovieDataSource
import com.binjar.sample.data.repositories.movie.MovieRepository


object Injector {
    fun provideMovieRepository(application: Application): MovieDataSource {
        return MovieRepository.getInstance(application, MovieApi.getInstance(application))
    }
}
