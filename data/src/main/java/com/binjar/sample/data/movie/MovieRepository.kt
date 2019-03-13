package com.binjar.sample.data.movie

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.binjar.sample.data.movie.paging.Listing
import com.binjar.sample.data.movie.model.Movie
import com.binjar.sample.data.movie.network.MovieApi
import io.reactivex.disposables.CompositeDisposable


class MovieRepository private constructor(val api: MovieApi) : MovieDataSource {

    override fun discoverMovies(until: String, compositeDisposable: CompositeDisposable): Listing<Movie> {
        val sourceFactory = MoviePagedSource.Factory(until, api, compositeDisposable)
        val sourceConfig = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build()

        val livePagedList = LivePagedListBuilder(sourceFactory, sourceConfig).build()
        val refreshState = Transformations.switchMap(sourceFactory.movieDSLiveData) { it.initialLoad }

        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.movieDSLiveData) { it.networkState },
                retry = {
                    sourceFactory.movieDSLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.movieDSLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(api: MovieApi) = instance
                ?: synchronized(this) {
                    instance
                            ?: MovieRepository(api).also { instance = it }
                }
    }
}
