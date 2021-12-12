package ru.ksart.tmdb_movies.domain.repository

interface SettingsRepository {

    suspend fun getLanguage(): String

}
