package ru.ksart.tmdb_movies.ui.extension

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import ru.ksart.tmdb_movies.R

fun SharedPreferences.setTheme(context: Context) {
    val isDarkTheme = this.getBoolean(
        context.getString(R.string.settings_dark_theme_key),
        context.resources.getBoolean(R.bool.settings_dark_theme_switch_value)
    )
    if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}
