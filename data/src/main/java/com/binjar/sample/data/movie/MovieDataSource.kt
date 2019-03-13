package com.binjar.sample.data.movie


import com.binjar.sample.data.movie.paging.Listing
import com.binjar.sample.data.movie.model.Movie
import io.reactivex.disposables.CompositeDisposable


interface MovieDataSource {

    fun discoverMovies(until: String, compositeDisposable: CompositeDisposable): Listing<Movie>
}