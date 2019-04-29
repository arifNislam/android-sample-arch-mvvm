package com.binjar.sample.data.movie.model

/**
 * @author Md Ariful Islam
 * @since Apr 28, 2019
 */

data class SortBy (
        val title: String,
        val value: String,
        @SortOrder val order: String = SortOrder.DESC
)

val sortOptions = arrayListOf(

        SortBy("Title (Ascending)", "original_title", SortOrder.ASC),
        SortBy("Title (Descending)", "original_title", SortOrder.DESC),
        SortBy("Release (Ascending)", "release_date", SortOrder.ASC),
        SortBy("Release (Descending)", "release_date", SortOrder.DESC),
        SortBy("Most Popular", "popularity", SortOrder.DESC),
        SortBy("Least Popular", "popularity", SortOrder.ASC),
        SortBy("Highest Grossing", "revenue", SortOrder.DESC),
        SortBy("Lowest Grossing", "revenue", SortOrder.ASC)
)
