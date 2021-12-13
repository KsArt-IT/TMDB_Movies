package ru.ksart.tmdb_movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.data.entitis.Movie
import ru.ksart.tmdb_movies.data.entitis.UiState
import ru.ksart.tmdb_movies.data.network.TmdbApiService
import ru.ksart.tmdb_movies.databinding.FragmentMovieDetailBinding
import ru.ksart.tmdb_movies.ui.extension.exhaustive

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_image)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieDetailBinding.inflate(inflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserveViewModel()
        viewModel.getMovieById(args.id)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initObserveViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        when (state) {
                            is UiState.Success -> uiRender(state.data)
                            is UiState.Loading -> {}
                            is UiState.Error -> {}
                        }.exhaustive
                    }
                }
            }
        }
    }

    private fun uiRender(item: Movie) {
        binding.run {
            movieImage.apply { transitionName = item.id.toString() }
                .load(TmdbApiService.getPathBigPoster(item.posterPath)) {
                    listener(
                        onSuccess = { _, _ ->
                            startPostponedEnterTransition()
                        },
                        onError = { request: ImageRequest, throwable: Throwable ->
                            startPostponedEnterTransition()
                        }
                    )
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
}
