package ru.ksart.tmdb_movies.ui.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.entitis.UiAction
import ru.ksart.tmdb_movies.data.entitis.UiEvent
import ru.ksart.tmdb_movies.data.entitis.UiState
import ru.ksart.tmdb_movies.domain.entities.Results
import ru.ksart.tmdb_movies.domain.usecase.language.GetMoviesLanguageUseCase
import ru.ksart.tmdb_movies.domain.usecase.language.RegisterChangeSettingsUseCase
import ru.ksart.tmdb_movies.domain.usecase.language.UnregisterChangeSettingsUseCase
import ru.ksart.tmdb_movies.domain.usecase.top_rated.GetMoviesTopRatedUseCase
import ru.ksart.tmdb_movies.ui.extension.exhaustive
import javax.inject.Inject

@HiltViewModel
class MoviesTopRatedViewModel @Inject constructor(
    private val getMoviesTopRated: GetMoviesTopRatedUseCase,
    private val getMoviesLanguage: GetMoviesLanguageUseCase,
    private val registerChangeSettings: RegisterChangeSettingsUseCase,
    private val unregisterChangeSettings: UnregisterChangeSettingsUseCase,
) : ViewModel() {

    val uiState: StateFlow<UiState<PagingData<MovieTop>>>

    private val _uiEvent = Channel<UiEvent<MovieTop>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiAction: (UiAction<MovieTop>) -> Unit

    init {
        viewModelScope.launch {
            registerChangeSettings()
        }

        uiAction = { action ->
            viewModelScope.launch {
                when (action) {
                    is UiAction.Click -> _uiEvent.send(UiEvent.Success(action.data))
                    is UiAction.Scroll -> {}
                }.exhaustive
            }
        }

        val pagingData = getMoviesLanguage.observe()
            .filterIsInstance<Results.Success<String>>()
            .flatMapLatest { result ->
                getMoviesTopRated.observe(result.data)
            }.mapNotNull { state ->
                when (state) {
                    is UiState.Success -> state.data
                    is UiState.Error -> {
                        UiEvent.Error(state.message)
                        null
                    }
                    is UiState.Loading -> null
                }
            }.cachedIn(viewModelScope)

        uiState = pagingData.map { data ->
            UiState.Success(data)
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = UiState.Loading
        )
    }

    override fun onCleared() {
        viewModelScope.launch {
            unregisterChangeSettings()
        }
        super.onCleared()
    }
}
