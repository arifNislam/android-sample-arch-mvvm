package com.binjar.sample.data.movie


import com.binjar.sample.data.movie.model.MovieResponse
import io.reactivex.Flowable


interface MovieDataSource {

    fun discoverMovies(until: String, page: Int): Flowable<MovieResponse>
}