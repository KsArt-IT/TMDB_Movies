package ru.ksart.tmdb_movies.data.entitis

import androidx.annotation.StringRes

sealed class UiEvent<out T : Any> {

    data class Success<out T : Any>(val data: T) : UiEvent<T>()

    data class Toast(@StringRes val stringId: Int) : UiEvent<Nothing>()

    data class Error(val message: String = "") : UiEvent<Nothing>()

}
