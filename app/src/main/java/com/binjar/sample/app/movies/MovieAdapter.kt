package com.binjar.sample.app.movies

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binjar.sample.app.NetworkStateItemViewHolder
import com.binjar.sample.app.R
import com.binjar.sample.data.NetworkState
import com.binjar.sample.data.movie.model.Movie


class MovieAdapter(val retryCallback: () -> Unit, val itemCallback: (Int, Movie) -> Unit) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id && oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        }
    }

    private var networkState: NetworkState? = null


    private val dataset = ArrayList<Any>()

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_network_state -> NetworkStateItemViewHolder.create(parent) {
                retryCallback()
            }
            R.layout.item_movie -> MovieItemViewHolder.create(parent) { adapterPosition: Int ->
                itemCallback(adapterPosition, dataset[adapterPosition] as Movie)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> getItem(position)?.let { movie ->
                (holder as MovieItemViewHolder).bind(movie)
            }
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

}
