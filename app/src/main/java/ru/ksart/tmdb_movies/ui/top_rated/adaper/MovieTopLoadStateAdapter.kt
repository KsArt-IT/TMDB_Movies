package ru.ksart.tmdb_movies.ui.top_rated.adaper

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class MovieTopLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieTopLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MovieTopLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        MovieTopLoadStateViewHolder.create(parent, retry)
}
