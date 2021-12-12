package ru.ksart.tmdb_movies.data.entitis

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieTopRatedResponse(
    @Json(name = "page")
    val page: Int = 0,
    @Json(name = "results")
    val results: List<MovieTop> = emptyList(),
    @Json(name = "total_results")
    val totalResults: Int = 0,
    @Json(name = "total_pages")
    val totalPages: Int = 0,
)
