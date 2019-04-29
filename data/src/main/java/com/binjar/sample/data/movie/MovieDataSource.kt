package com.binjar.sample.data.movie


import com.binjar.sample.data.movie.model.MovieResponse
import com.binjar.sample.data.movie.model.SortBy
import io.reactivex.Flowable


interface MovieDataSource {

    fun discoverMovies(page: Int, sortBy: SortBy): Flowable<MovieResponse>
}