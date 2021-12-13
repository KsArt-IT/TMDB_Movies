package ru.ksart.tmdb_movies.ui.top_rated.adaper

import androidx.recyclerview.widget.DiffUtil
import ru.ksart.tmdb_movies.data.entitis.MovieTop

class MovieTopDiffCallback : DiffUtil.ItemCallback<MovieTop>() {
    override fun areItemsTheSame(oldItem: MovieTop, newItem: MovieTop): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieTop, newItem: MovieTop): Boolean {
        return oldItem == newItem
    }
}
