package ru.ksart.tmdb_movies.data.entitis

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenre(
    val id: Int?,
    val name: String?,
)
