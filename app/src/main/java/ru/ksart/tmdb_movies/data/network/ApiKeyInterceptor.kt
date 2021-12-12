package ru.ksart.tmdb_movies.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // модифицируем Url
        val modifiedUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        // модифицируем запрос
        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }

    private companion object {
        const val API_KEY = "893f6376edec1b857f7779dcbc0b57cc"
    }
}
// https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
