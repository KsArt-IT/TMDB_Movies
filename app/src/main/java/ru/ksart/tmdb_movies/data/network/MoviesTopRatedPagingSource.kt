package ru.ksart.tmdb_movies.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.ksart.tmdb_movies.data.entitis.MovieTop
import ru.ksart.tmdb_movies.domain.repository.SettingsRepository
import java.io.IOException

class MoviesTopRatedPagingSource(
    private val service: TmdbApiService,
    private val settings: SettingsRepository,
) : PagingSource<Int, MovieTop>() {

    override fun getRefreshKey(state: PagingState<Int, MovieTop>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTop> {
        val position = params.key ?: TmdbApiService.STARTING_PAGE_INDEX
        return try {
            val language = settings.getLanguage()
            val response = service.getMovieTop(page = position, language = language)
            if (response.isSuccessful) {
                val result = response.body()?.results ?: emptyList()
                val nextKey = if (result.isEmpty()) null else position + 1
                val prevKey =
                    if (position == TmdbApiService.STARTING_PAGE_INDEX) null else position - 1
                LoadResult.Page(
                    data = result,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
