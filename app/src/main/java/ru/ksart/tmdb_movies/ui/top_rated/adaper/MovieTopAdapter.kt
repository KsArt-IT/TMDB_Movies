package ru.ksart.tmdb_movies.ui.top_rated.adaper

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.entitis.UiAction

class MovieTopAdapter(
    private val onClick: (UiAction<MovieTop>) -> Unit
) : PagingDataAdapter<MovieTop, MovieTopViewHolder>(MovieTopDiffCallback()) {

    override fun onBindViewHolder(holder: MovieTopViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTopViewHolder {
        return MovieTopViewHolder.create(parent, onClick)
    }
}
