package ru.ksart.tmdb_movies.data.entitis

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieGenre(
    val id: Int?,
    val name: String?,
): Parcelable
