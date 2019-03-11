package com.binjar.sample.data.movie.model

import com.google.gson.annotations.SerializedName


data class MovieResponse(
        @SerializedName("page") val page: Int = 1,
        @SerializedName("total_results") val totalResults: Int = 0,
        @SerializedName("total_pages") val totalPages: Int = 1,
        @SerializedName("results") val movies: ArrayList<Movie>
)
