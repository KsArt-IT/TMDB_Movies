package ru.ksart.tmdb_movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.ksart.tmdb_movies.data.entitis.Movie
import ru.ksart.tmdb_movies.data.entitis.MovieTop

interface MoviesRepository {

    suspend fun getMovieById(id: Long, language: String): Movie

    fun getMoviesTopRated(language: String): Flow<PagingData<MovieTop>>
}
