package ru.ksart.tmdb_movies.ui.top_rated.adaper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.network.TmdbApiService
import ru.ksart.tmdb_movies.databinding.ItemMoviesTopRatedBinding

class MovieTopViewHolder(
    private val binding: ItemMoviesTopRatedBinding,
    private val onClick: (MovieTop, ImageView) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    var item: MovieTop? = null
        private set

    init {
        binding.run { root.setOnClickListener { item?.let { onClick(it, movieImage) } } }
    }

    fun bind(item: MovieTop) {
        this.item = item

        binding.run {
            movieImage.apply { transitionName = item.id.toString() }
                .load(TmdbApiService.getPathSmallPoster(item.posterPath)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_download_24)
                    error(R.drawable.ic_error_24)
                    build()
                }
            movieTitle.text = item.title ?: ""
            movieReleaseDate.text = item.releaseDate ?: ""
            movieGenre.text = TmdbApiService.getGenreByListId(binding.root.context, item.genreIds)
            movieOverview.text = item.overview ?: ""
            moviePopularity.text = item.popularity.toString()
            binding.movieVoteAverage.text = item.voteAverage.toString()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (MovieTop, ImageView) -> Unit,
        ) = ItemMoviesTopRatedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { MovieTopViewHolder(it, onClick) }
    }
}
