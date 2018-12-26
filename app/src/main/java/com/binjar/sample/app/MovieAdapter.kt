package com.binjar.sample.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    companion object {
        const val TYPE_HEADER = 11
        const val TYPE_ITEM = 22
        const val TYPE_LOADER = 33
    }

    private val dataset = ArrayList<Movie>()

    var itemClickListener: ItemClickListener<Movie>? = null

    fun addItems(movies: ArrayList<Movie>) {
        dataset.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataset.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return ItemViewHolder(itemView).apply {
            itemLayout.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition, dataset[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(dataset[position])
    }

    abstract class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Movie)
    }

    class ItemViewHolder(itemView: View) : MovieHolder(itemView) {

        val itemLayout = itemView.findViewById<View>(R.id.itemLayout)
        private val titleView = itemView.findViewById<AppCompatTextView>(R.id.titleView)

        override fun bind(item: Movie) {
            titleView.text = item.title
        }

    }
}
