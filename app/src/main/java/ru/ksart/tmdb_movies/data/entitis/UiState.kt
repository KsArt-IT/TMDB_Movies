package ru.ksart.tmdb_movies.data.entitis

sealed class UiState<out T : Any> {

    data class Success<out T : Any>(val data: T) : UiState<T>()

    data class Error(val message: String = "") : UiState<Nothing>()

    object Loading : UiState<Nothing>()

}
