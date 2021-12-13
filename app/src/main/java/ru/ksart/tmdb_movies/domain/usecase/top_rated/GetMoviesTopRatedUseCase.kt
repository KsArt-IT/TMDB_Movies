package ru.ksart.tmdb_movies.domain.usecase.top_rated

import android.content.Context
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.entitis.UiState
import ru.ksart.tmdb_movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesTopRatedUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MoviesRepository,
) {

    fun observe(params: String): Flow<UiState<PagingData<MovieTop>>> =
        repository.getMoviesTopRated(params).map { data ->
            UiState.Success(data)
        }.catch {
            UiState.Error(
                it.localizedMessage ?: context.getString(R.string.error_loading_movies)
            )
        }
}
