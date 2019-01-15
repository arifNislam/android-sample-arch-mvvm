package com.binjar.sample.data

import android.app.Application
import com.binjar.sample.data.repositories.movie.MovieApi
import com.binjar.sample.data.repositories.movie.MovieRepository
import com.binjar.sample.data.repositories.movie.MovieRepositoryImpl


object Injector {
    fun provideMovieRepository(application: Application): MovieRepository {
        return MovieRepositoryImpl.getInstance(MovieApi.getInstance(application))
    }
}
