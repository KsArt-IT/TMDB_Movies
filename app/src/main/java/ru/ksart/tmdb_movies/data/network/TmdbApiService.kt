package ru.ksart.tmdb_movies.data.network

import android.content.Context
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ksart.tmdb_movies.R
import ru.ksart.tmdb_movies.data.entitis.Movie
import ru.ksart.tmdb_movies.data.entitis.MovieTopRatedResponse

interface TmdbApiService {

    @GET("movie/top_rated")
    suspend fun getMovieTop(
        @Query(PARAMS_PAGE) page: Int,
        @Query(PARAMS_LANGUAGE) language: String,
    ): Response<MovieTopRatedResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query(PARAMS_LANGUAGE) language: String,
    ): Movie

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 40
        const val PARAMS_PAGE = "page"
        const val PARAMS_LANGUAGE = "language"

        private const val POSTER_URL = "https://image.tmdb.org/t/p/"
        private const val POSTER_SIZE_BIG = "w780"
        private const val POSTER_SIZE_SMALL = "w185"

        fun getPathSmallPoster(file: String?) =
            file?.let { "$POSTER_URL$POSTER_SIZE_SMALL$file" } ?: ""

        fun getPathBigPoster(file: String?) = file?.let { "$POSTER_URL$POSTER_SIZE_BIG$file" } ?: ""

        fun getGenreByListId(context: Context, list: List<Int>?): String {
            return list?.joinToString { getGenreId(context, it) } ?: ""
        }

        private fun getGenreId(context: Context, id: Int): String = when (id) {
            12 -> context.getString(R.string.genre_adventure)
            14 -> context.getString(R.string.genre_fantasy)
            16 -> context.getString(R.string.genre_animation)
            18 -> context.getString(R.string.genre_drama)
            27 -> context.getString(R.string.genre_horror)
            28 -> context.getString(R.string.genre_action)
            35 -> context.getString(R.string.genre_comedy)
            36 -> context.getString(R.string.genre_history)
            37 -> context.getString(R.string.genre_western)
            53 -> context.getString(R.string.genre_thriller)
            80 -> context.getString(R.string.genre_crime)
            99 -> context.getString(R.string.genre_documentary)
            878 -> context.getString(R.string.genre_science_fiction)
            9648 -> context.getString(R.string.genre_mystery)
            10402 -> context.getString(R.string.genre_music)
            10749 -> context.getString(R.string.genre_romance)
            10751 -> context.getString(R.string.genre_family)
            10752 -> context.getString(R.string.genre_war)
            10770 -> context.getString(R.string.genre_tv_movie)
            else -> context.getString(R.string.genre_no_info)
        }
    }

}
/*
get/movie/{movie_id}
https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US

get/movie/top_rated
https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1

get/movie/popular
https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
 */
