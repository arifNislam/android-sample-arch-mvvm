package com.binjar.sample.data.repositories.movie


import com.binjar.sample.data.repositories.movie.model.Movie
import io.reactivex.Flowable
import java.text.SimpleDateFormat
import java.util.*


interface MovieDataSource {

    private val dateFormatter: SimpleDateFormat
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun discoverMovies(until: String = dateFormatter.format(Date())): Flowable<ArrayList<Movie>>
}