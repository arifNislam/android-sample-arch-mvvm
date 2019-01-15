package com.binjar.sample.app.movies

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binjar.sample.app.core.AppViewModel
import com.binjar.sample.data.repositories.movie.MovieRepository
import io.reactivex.disposables.CompositeDisposable


class MovieViewModel private constructor(private val repository: MovieRepository) : AppViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val queryUntil = MutableLiveData<String>()

    private val movieResult = Transformations.map(queryUntil) { query ->
        repository.discoverMovies(query, compositeDisposable)
    }

    var discoveredMovies = Transformations.switchMap(movieResult) { it.pagedList }
    var networkState  = Transformations.switchMap(movieResult) {it.networkState}

    override fun refresh() {
        compositeDisposable.clear()
    }

    fun discover(date: String): Boolean {
        if (date == queryUntil.value) {
            return false
        }
        queryUntil.value = date
        return true
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    class Factory(private var dataSource: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

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
