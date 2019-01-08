package com.binjar.sample.data.repositories.movie.model

import com.google.gson.annotations.SerializedName


data class Movie(
        @SerializedName("id") val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("poster_path") val poster: String,
        @SerializedName("release_date") val releaseDate: String
)
