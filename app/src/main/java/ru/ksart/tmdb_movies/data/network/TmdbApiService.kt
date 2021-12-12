package ru.ksart.tmdb_movies.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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
        const val STARTING_PAGE_INDEX = 0
        const val PAGE_SIZE = 40
        const val PARAMS_PAGE = "page"
        const val PARAMS_LANGUAGE = "language"
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
