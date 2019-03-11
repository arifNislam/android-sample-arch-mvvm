package com.binjar.sample.data.movie


import com.binjar.sample.data.Listing
import com.binjar.sample.data.movie.model.Movie
import io.reactivex.disposables.CompositeDisposable


interface MovieRepository {

    fun discoverMovies(until: String, compositeDisposable: CompositeDisposable): Listing<Movie>
}