package ru.ksart.tmdb_movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.ksart.tmdb_movies.data.entitis.Movie
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.entitis.UiEvent
import ru.ksart.tmdb_movies.data.entitis.UiState
import ru.ksart.tmdb_movies.domain.entities.Results
import ru.ksart.tmdb_movies.domain.usecase.language.GetMoviesLanguageUseCase
import ru.ksart.tmdb_movies.domain.usecase.top_rated.GetMovieByIdUseCase
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieById: GetMovieByIdUseCase,
    private val getMoviesLanguage: GetMoviesLanguageUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<MovieTop>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getMovieById(id: Long) {
        viewModelScope.launch {
            val language = getMoviesLanguage()
            when (val result = getMovieById(id to language)) {
                is Results.Success -> _uiState.value = UiState.Success(result.data)
                is Results.Error -> _uiEvent.send(UiEvent.Error(result.message))
            }
        }
    }
}
