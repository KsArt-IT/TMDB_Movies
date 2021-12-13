package ru.ksart.tmdb_movies.ui.top_rated

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.entitis.UiAction
import ru.ksart.tmdb_movies.data.entitis.UiEvent
import ru.ksart.tmdb_movies.data.entitis.UiState
import ru.ksart.tmdb_movies.databinding.FragmentMoviesTopRatedBinding
import ru.ksart.tmdb_movies.ui.extension.exhaustive
import ru.ksart.tmdb_movies.ui.extension.toast
import ru.ksart.tmdb_movies.ui.top_rated.adaper.MovieTopAdapter
import ru.ksart.tmdb_movies.ui.top_rated.adaper.MovieTopLoadStateAdapter

@AndroidEntryPoint
class MoviesTopRatedFragment : Fragment(R.layout.fragment_movies_top_rated) {

    private var _binding: FragmentMoviesTopRatedBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: MoviesTopRatedViewModel by viewModels()

    private var _movieAdapter: MovieTopAdapter? = null
    private val movieAdapter get() = checkNotNull(_movieAdapter) { "MovieTop adapter is`t initialized" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMoviesTopRatedBinding.inflate(inflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListener()
        initObserveViewModel()
        // для анимированного перехода
        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onDestroyView() {
        _binding = null
        _movieAdapter = null
        super.onDestroyView()
    }

    private fun initAdapter() {
        _movieAdapter = MovieTopAdapter(::showMovieTopDetail)
        binding.movieTopList.run {
            adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieTopLoadStateAdapter { movieAdapter.retry() },
                footer = MovieTopLoadStateAdapter { movieAdapter.retry() }
            )
            movieAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            layoutManager = LinearLayoutManager(requireContext())

            setHasFixedSize(true)
            isNestedScrollingEnabled = false

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy != 0) viewModel.uiAction(UiAction.Scroll)
                }
            })
        }
    }

    private fun initListener() {
        binding.retryButton.setOnClickListener { movieAdapter.retry() }
    }

    private fun initObserveViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        when (state) {
                            is UiState.Success -> {
//                                showLoading(false)
                                movieAdapter.submitData(state.data)
                            }
                            is UiState.Loading -> showLoading(true)
                            is UiState.Error -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is UiEvent.Success -> {}
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Error -> toast(event.message)
                        }.exhaustive
                    }
                }
                launch {
                    movieAdapter.loadStateFlow.collect { loadState ->
                        val isListEmpty =
                            loadState.refresh is LoadState.NotLoading && movieAdapter.itemCount == 0
                        binding.run {
                            // show empty list
                            emptyListTextView.isVisible = isListEmpty
                            // Only show the list if refresh succeeds.
                            movieTopList.isVisible = !isListEmpty
                            // Show loading spinner during initial load or refresh.
                            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                            // Show the retry state if initial load or refresh fails.
                            retryButton.isVisible = loadState.source.refresh is LoadState.Error
                        }

                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showMovieTopDetail(item: MovieTop, imageView: ImageView) {
        Log.d("tagMovie", "MoviesTopRatedFragment: showMovieTopDetail")
        val extras = FragmentNavigatorExtras(
            imageView to item.id.toString()
        )
        val action =
            MoviesTopRatedFragmentDirections.actionTopRatedMoviesFragmentToMovieDetailFragment(item.id)
        findNavController().navigate(action, extras)
    }
}
