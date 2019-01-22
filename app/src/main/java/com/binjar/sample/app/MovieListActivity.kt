package com.binjar.sample.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mauker.materialsearchview.MaterialSearchView
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

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                title = query
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun initAdapter() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        movieRecyclerView.layoutManager = layoutManager

        movieAdapter = MovieAdapter({
            viewModel.refresh()
        }, { _: Int, movie: Movie ->
            showSnackMessage(container, movie.title)
        })
        movieRecyclerView.adapter = movieAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (results?.isNotEmpty() == true) {
                searchView.setQuery(results[0], true)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search) {
            if (!searchView.isOpen) {
                searchView.openSearch()
            }
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (searchView.isOpen) {
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }
}

