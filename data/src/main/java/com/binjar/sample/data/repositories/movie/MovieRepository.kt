package com.binjar.sample.data.repositories.movie


import com.binjar.sample.data.repositories.Listing
import com.binjar.sample.data.repositories.movie.model.Movie
import io.reactivex.disposables.CompositeDisposable


interface MovieRepository {

    fun discoverMovies(until: String, compositeDisposable: CompositeDisposable): Listing<Movie>
}