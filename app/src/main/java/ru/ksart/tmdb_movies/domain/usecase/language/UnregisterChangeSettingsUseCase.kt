package ru.ksart.tmdb_movies.domain.usecase.language

import ru.ksart.tmdb_movies.domain.repository.SettingsRepository
import javax.inject.Inject

class UnregisterChangeSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke() {
        repository.unregisterChangeSettings()
    }
}
