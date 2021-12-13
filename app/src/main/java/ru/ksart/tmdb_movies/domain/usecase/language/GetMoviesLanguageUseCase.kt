package ru.ksart.tmdb_movies.domain.usecase.language

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ksart.tmdb_movies.domain.entities.Results
import ru.ksart.tmdb_movies.domain.repository.SettingsRepository
import javax.inject.Inject

class GetMoviesLanguageUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    fun observe(): Flow<Results<String>> = repository.language.map { data ->
        Results.Success(data)
    }.catch {
        "en-US"
        Results.Success("en-US")
    }

    suspend operator fun invoke(): String {
        return try {
            repository.getLanguageMovies()
        } catch (e: Exception) {
            "en-US"
        }
    }

}
