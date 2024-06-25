package com.example.moviesdb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesdb.data.model.CastMember
import com.example.moviesdb.data.remote.MovieApi

class CastPagingSource(
    private val movieApi: MovieApi,
    private val movieID: Int
) : PagingSource<Int, CastMember>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CastMember> {
        val currentPage = params.key ?: 1
        return try {
            val response = movieApi.getCast(movieID = movieID, page = currentPage)
            val endOfPaginationReached = response.cast.isEmpty()
            if (response.cast.isNotEmpty()) {
                LoadResult.Page(
                    data = response.cast,
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

    override fun getRefreshKey(state: PagingState<Int, CastMember>): Int? {
        return state.anchorPosition
    }

}