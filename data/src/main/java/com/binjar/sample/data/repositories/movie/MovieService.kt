package com.binjar.sample.data.repositories.movie

import com.binjar.sample.data.BuildConfig
import com.binjar.sample.data.repositories.movie.model.MovieResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY: String = BuildConfig.API_KEY

interface MovieService {

    @GET("discover/movie?api_key=$API_KEY")
    fun discover(@Query("primary_release_date.lte") date: String): Flowable<Response<MovieResponse>>
}
