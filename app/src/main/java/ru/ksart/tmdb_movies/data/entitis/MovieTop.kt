package ru.ksart.tmdb_movies.data.entitis

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieTop(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "release_date")
    val releaseDate: String?,

    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "overview")
    val overview: String?,

    @Json(name = "genre_ids")
    val genreIds: List<Int>?,

    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    @Json(name = "vote_average")
    val voteAverage: Double?,

    @Json(name = "video")
    val video: Boolean?,
)
/*
"poster_path": "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
      "adult": false,
      "overview": "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
      "release_date": "1994-09-10",
      "genre_ids": [
        18,
        80
      ],
      "id": 278,
      "original_title": "The Shawshank Redemption",
      "original_language": "en",
      "title": "The Shawshank Redemption",
      "backdrop_path": "/xBKGJQsAIeweesB79KC89FpBrVr.jpg",
      "popularity": 6.741296,
      "vote_count": 5238,
      "video": false,
      "vote_average": 8.32
 */
