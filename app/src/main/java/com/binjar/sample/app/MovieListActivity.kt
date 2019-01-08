package com.binjar.sample.app

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binjar.sample.app.core.BaseActivity
import com.binjar.sample.app.core.ItemClickListener
import com.binjar.sample.app.movies.MovieAdapter
import com.binjar.sample.app.movies.MovieViewModel
import com.binjar.sample.data.Injector
import com.binjar.sample.data.repositories.movie.model.Movie
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : BaseActivity() {

    lateinit var movieAdapter: MovieAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

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

        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        movieRecyclerView.layoutManager = layoutManager
        movieAdapter = MovieAdapter()

        movieRecyclerView.adapter = movieAdapter
        movieAdapter.itemClickListener = object : ItemClickListener<Movie> {
            override fun onItemClick(position: Int, item: Movie) {
                showSnackMessage(container, item.title)
            }
        }

        viewModel.snackMessage.observe(this, Observer { text ->
            if (text != null && !text.isEmpty()) {
                showSnackMessage(container, text)
            }
        })

        viewModel.loader.observe(this, Observer { loading ->
            if (loading) {
                loadingView.visibility = View.VISIBLE
            } else {
                loadingView.visibility = View.GONE
            }
        })

        viewModel.discoveredMovies.observe(this, Observer { movies -> movieAdapter.addItems(movies as ArrayList<Movie>) })

        viewModel.discover()
    }
}

