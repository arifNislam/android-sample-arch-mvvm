package com.binjar.sample.data.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.binjar.sample.data.NetworkState
import com.binjar.sample.data.movie.model.Movie
import com.binjar.sample.data.movie.model.MovieResponse
import io.reactivex.disposables.CompositeDisposable


class MoviePagedSource private constructor(
        private val compositeDisposable: CompositeDisposable,
        private val movieDataSource: MovieDataSource,
        private val queryUntil: String
) : PageKeyedDataSource<Int, Movie>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        val disposable = movieDataSource.discoverMovies(queryUntil, params.key)
                .subscribe({ movieResponse: MovieResponse? ->
                    val movies = movieResponse?.movies ?: emptyList<Movie>()
                    callback.onResult(movies, params.key + 1)
                    networkState.postValue(NetworkState.LOADED)
                }, { error: Throwable ->
                    networkState.postValue(NetworkState.error(error.message))
                })
        compositeDisposable.add(disposable)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val disposable = movieDataSource.discoverMovies(queryUntil, 1)
                .subscribe(({ movieResponse: MovieResponse? ->
                    val movies = movieResponse?.movies ?: emptyList<Movie>()
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(movies, 0, movieResponse?.totalResults ?: 0, 1, 2)
                }), ({ error: Throwable? ->
                    val networkError = NetworkState.error(error?.message ?: "Unknown error")
                    networkState.postValue(networkError)
                    initialLoad.postValue(networkError)
                }))

        compositeDisposable.add(disposable)
    }

    class Factory(
            private val compositeDisposable: CompositeDisposable,
            private val movieDataSource: MovieDataSource,
            private val queryUntil: String
    ) : DataSource.Factory<Int, Movie>() {

        val movieDSLiveData = MutableLiveData<MoviePagedSource>()

        override fun create(): DataSource<Int, Movie> {
            val source = MoviePagedSource(compositeDisposable, movieDataSource, queryUntil)
            movieDSLiveData.postValue(source)
            return source
        }
    }
}
