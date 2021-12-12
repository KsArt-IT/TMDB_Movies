package ru.ksart.tmdb_movies.data.repository

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.di.IoDispatcher
import ru.ksart.tmdb_movies.domain.repository.SettingsRepository
import java.util.*
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

    override suspend fun getLanguage(): String = withContext(dispatcher) {
        val language = defaultPreferences.getString(
            languageKey,
            res.getString(R.string.settings_language_value)
        ) ?: ""
        when (language.ifBlank { Locale.getDefault().language }) {
            "ru" -> "ru-RU"
            "ua" -> "uk-UA"
            "en" -> "en-US"
            else -> "en-US"
        }
    }
}
