package ru.ksart.tmdb_movies.domain.usecase.top_rated

import ru.ksart.tmdb_movies.data.entitis.Movie
import ru.ksart.tmdb_movies.domain.entities.Results
import ru.ksart.tmdb_movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {

    suspend operator fun invoke(params: Pair<Long, String>): Results<Movie> {
        return try {
            Results.Success(repository.getMovieById(params.first, params.second))
        } catch (e: Exception) {
            Results.Error(e.localizedMessage)
        }
    }
}
