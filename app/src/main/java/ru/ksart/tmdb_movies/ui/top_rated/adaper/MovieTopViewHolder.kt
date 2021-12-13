package ru.ksart.tmdb_movies.ui.top_rated.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.entitis.UiAction
import ru.ksart.tmdb_movies.data.network.TmdbApiService
import ru.ksart.tmdb_movies.databinding.ItemMoviesTopRatedBinding

class MovieTopViewHolder(
    private val binding: ItemMoviesTopRatedBinding,
    onClick: (UiAction<MovieTop>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    var item: MovieTop? = null
        private set

    init {
        binding.root.setOnClickListener {
            item?.let { onClick(UiAction.Click(it)) }
        }
    }

    fun bind(item: MovieTop) {
        this.item = item

        binding.run {
            movieImage.load(TmdbApiService.getPathSmallPoster(item.posterPath)) {
                crossfade(true)
                placeholder(R.drawable.ic_download_24)
                error(R.drawable.ic_error_24)
                build()
            }
            movieTitle.text = item.title ?: ""
            binding.movieVoteAverage.text = item.voteAverage.toString()
            moviePopularity.text = item.popularity.toString() ?: ""
            movieOverview.text = item.overview ?: ""
            movieGenre.text = TmdbApiService.getGenreByListId(binding.root.context, item.genreIds)
            movieReleaseDate.text = item.releaseDate ?: ""
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (UiAction<MovieTop>) -> Unit
        ) = ItemMoviesTopRatedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { MovieTopViewHolder(it, onClick) }
    }
}
