package ru.ksart.tmdb_movies.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.ksart.tmdb_movies.data.entitis.Movie
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.data.network.MoviesTopRatedPagingSource
import ru.ksart.tmdb_movies.data.network.TmdbApiService
import ru.ksart.tmdb_movies.di.IoDispatcher
import ru.ksart.tmdb_movies.domain.repository.MoviesRepository
import ru.ksart.tmdb_movies.domain.repository.SettingsRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val service: TmdbApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : MoviesRepository {

    override suspend fun getMovieById(id: Int, language: String): Movie = withContext(dispatcher) {
        service.getMovieById(movieId = id, language = language)
    }

    override fun getMoviesTopRated(language: String): Flow<PagingData<MovieTop>> {
        return Pager(
            config = PagingConfig(pageSize = TmdbApiService.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesTopRatedPagingSource(service, language) },
        ).flow
            .flowOn(dispatcher)

    }
}
