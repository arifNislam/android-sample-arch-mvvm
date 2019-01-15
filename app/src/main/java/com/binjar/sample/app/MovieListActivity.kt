package com.binjar.sample.app

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binjar.sample.app.core.BaseActivity
import com.binjar.sample.app.movies.MovieAdapter
import com.binjar.sample.app.movies.MovieViewModel
import com.binjar.sample.data.Injector
import com.binjar.sample.data.repositories.movie.model.Movie
import kotlinx.android.synthetic.main.activity_movie_list.*
import java.text.SimpleDateFormat
import java.util.*

class MovieListActivity : BaseActivity() {

    lateinit var movieAdapter: MovieAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this,
                MovieViewModel.Factory(Injector.provideMovieRepository(application)))
                .get(MovieViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setSupportActionBar(toolbar)
        setTitle(R.string.title_movies)

        initAdapter()

        viewModel.snackMessage.observe(this, Observer { text ->
            if (text != null && !text.isEmpty()) {
                showSnackMessage(container, text)
            }
        })

        viewModel.networkState.observe(this, Observer { state ->
            movieAdapter.setNetworkState(state)
        })

        viewModel.discoveredMovies.observe(this, Observer { movies ->
            movieAdapter.submitList(movies)
        })

        viewModel.discover(dateFormat.format(Date()))
    }

    private fun initAdapter() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        movieRecyclerView.layoutManager = layoutManager

        movieAdapter = MovieAdapter({
            viewModel.refresh()
        }, { position: Int, movie: Movie ->
            showSnackMessage(container, movie.title)
        })
        movieRecyclerView.adapter = movieAdapter
    }
}

