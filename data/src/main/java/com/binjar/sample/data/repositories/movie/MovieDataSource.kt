package com.binjar.sample.data.repositories.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.binjar.sample.data.NetworkState
import com.binjar.sample.data.repositories.movie.model.Movie
import com.binjar.sample.data.repositories.movie.model.MovieResponse
import io.reactivex.disposables.CompositeDisposable


class MovieDataSource private constructor(
        private val queryUntil: String,
        private val movieApi: MovieApi,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        val disposable = movieApi.discoverMovies(queryUntil, params.key)
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

        val disposable = movieApi.discoverMovies(queryUntil, 1)
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

    class Factory(private val queryUntil: String,
                  private val movieApi: MovieApi,
                  private val compositeDisposable: CompositeDisposable
    ) : DataSource.Factory<Int, Movie>() {

        val movieDSLiveData = MutableLiveData<MovieDataSource>()

        override fun create(): DataSource<Int, Movie> {
            val source = MovieDataSource(queryUntil, movieApi, compositeDisposable)
            movieDSLiveData.postValue(source)
            return source
        }
    }
}
