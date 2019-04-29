package com.binjar.sample.data.movie.network

import com.binjar.sample.data.movie.model.MovieResponse
import com.binjar.sample.data.movie.model.SortBy
import io.reactivex.Flowable

interface MovieNetworkSource {
    fun discoverMovies(page: Int, sortBy: SortBy) : Flowable<MovieResponse>
}
