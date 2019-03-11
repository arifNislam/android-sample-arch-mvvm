package com.binjar.sample.app.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.binjar.sample.app.R
import com.binjar.sample.data.movie.model.Movie

class MovieItemViewHolder(itemView: View, callback: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val itemLayout: View = itemView.findViewById<View>(R.id.itemLayout)
    private val titleView = itemView.findViewById<AppCompatTextView>(R.id.titleView)

    companion object {
        fun create(parent: ViewGroup, callback: (Int) -> Unit): MovieItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MovieItemViewHolder(view, callback)
        }
    }

    init {
        itemLayout.setOnClickListener {
            callback(adapterPosition)
        }
    }

    fun bind(item: Movie) {
        titleView.text = item.title
    }
}
