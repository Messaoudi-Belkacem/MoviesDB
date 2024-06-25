package com.example.moviesdb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.remote.MovieApi

class SearchPagingSource(
    private val movieApi: MovieApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPage = params.key ?: 1
        return try {
            val response = movieApi.searchMovies(query = query, page = currentPage)
            val endOfPaginationReached = response.results.isEmpty()
            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

}