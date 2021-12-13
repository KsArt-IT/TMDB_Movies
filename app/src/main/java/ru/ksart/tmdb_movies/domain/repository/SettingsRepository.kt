package ru.ksart.tmdb_movies.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

//    fun getLanguage(): Flow<String>
    suspend fun registerChangeSettings()
    suspend fun unregisterChangeSettings()
    val language: Flow<String>
}
