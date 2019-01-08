package com.binjar.sample.app.movies

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binjar.sample.app.core.AppViewModel
import com.binjar.sample.data.repositories.movie.MovieDataSource
import com.binjar.sample.data.repositories.movie.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MovieViewModel private constructor(private val dataSource: MovieDataSource) : AppViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var discoveredMovies = MutableLiveData<List<Movie>>()

    override fun refresh() {
        compositeDisposable.clear()
    }

    fun discover() {
        val disposable = dataSource.discoverMovies("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.value = true }
                .doOnTerminate { loader.value = false }
                .subscribe({ movies -> discoveredMovies.setValue(movies) },
                        { throwable -> snackMessage.setValue(throwable.message) })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    class Factory(internal var dataSource: MovieDataSource) : ViewModelProvider.NewInstanceFactory() {

        @NonNull
        override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
            if (MovieViewModel::class.java.isAssignableFrom(modelClass)) {
                try {
                    return MovieViewModel(dataSource) as T
                } catch (e: Exception) {
                    throw RuntimeException("Cannot create an instance of \$modelClass", e)
                }

            }
            return super.create(modelClass)
        }
    }
}
