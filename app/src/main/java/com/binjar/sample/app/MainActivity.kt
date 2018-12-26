package com.binjar.sample.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var movieAdapter: MovieAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        movieRecyclerView.layoutManager = layoutManager
        movieAdapter = MovieAdapter()

        movieAdapter.addItems(ArrayList<Movie>().apply {
            add(Movie(1, "Mowgli"))
            add(Movie(2, "Black Panther"))
            add(Movie(3, "Aquaman"))
            add(Movie(4, "Spiderman"))
            add(Movie(5, "Avengers: Endgame"))
            add(Movie(6, "American Hangman"))
            add(Movie(7, "Sgt. Will Gardner"))
            add(Movie(8, "Happy Death Day 2U"))
            add(Movie(9, "You Might Be the Killer"))
            add(Movie(10, "The Rainbow Experiment"))
            add(Movie(11, "Captain Marvel"))
            add(Movie(12, "Artemis Fowl"))
            add(Movie(13, "Tough Guy: The Bob Probert Story"))
            add(Movie(14, "The Lion King"))
            add(Movie(15, "Dead in a Week: Or Your Money Back"))
            add(Movie(16, "Piercing"))
            add(Movie(17, "Stan & Ollie"))
            add(Movie(18, "The LEGO Movie 2: The Second Part"))
            add(Movie(19, "Serenity"))
            add(Movie(20, "The Vanishing"))
        })

        movieRecyclerView.adapter = movieAdapter
        movieAdapter.itemClickListener = object : ItemClickListener<Movie> {
            override fun onItemClick(position: Int, item: Movie) {
                Snackbar.make(container, item.title, Snackbar.LENGTH_LONG).show()
            }

        }
    }
}

