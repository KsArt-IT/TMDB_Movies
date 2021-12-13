package ru.ksart.tmdb_movies.data.entitis

sealed class UiAction<out T : Any> {

    class Click<out T : Any>(val data: T) : UiAction<T>()

    object Scroll : UiAction<Nothing>()

}
