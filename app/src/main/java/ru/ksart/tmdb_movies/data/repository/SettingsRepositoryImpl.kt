package ru.ksart.tmdb_movies.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.di.IoDispatcher
import ru.ksart.tmdb_movies.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : SettingsRepository {

    private val res = context.resources

    private val defaultPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val firstStartKey: String by lazy { res.getString(R.string.first_start_key) }
    private val darkThemeKey: String by lazy { res.getString(R.string.settings_dark_theme_key) }
    private val languageKey: String by lazy { res.getString(R.string.settings_language_key) }

    private val _language = MutableStateFlow(getLanguageMovies())
    override val language = _language.asStateFlow()

    private val listener by lazy {
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            _language.value = getLanguageMovies()
        }
    }

    override suspend fun registerChangeSettings() {
        withContext(dispatcher) {
            defaultPreferences.registerOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun unregisterChangeSettings() {
        withContext(dispatcher) {
            defaultPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    private fun getLanguageMovies(): String {
        return when (defaultPreferences.getString(
            languageKey,
            res.getString(R.string.settings_language_value)
        ) ?: "") {
            "ru" -> "ru-RU"
            "ua" -> "uk-UA"
            "en" -> "en-US"
            else -> "en-US"
        }
    }
}
