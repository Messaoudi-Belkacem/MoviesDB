package com.example.moviesdb.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesdb.data.local.MovieDatabase
import com.example.moviesdb.data.model.ApiByPopularRemoteKeys
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.remote.MovieApi

@ExperimentalPagingApi
class ApiPopularRemoteMediator(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, MovieByPopular>() {

    private val apiMovieDao = movieDatabase.apiMovieByPopularDao()
    private val apiRemoteKeysDao = movieDatabase.apiByPopularRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieByPopular>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = movieApi.getAllMoviesByPopular(page = currentPage).results
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    apiMovieDao.deleteAllMovies()
                    apiRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { movie ->
                    ApiByPopularRemoteKeys(
                        id = movie.id.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                apiRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                apiMovieDao.addMovies(movies = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieByPopular>
    ): ApiByPopularRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                apiRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieByPopular>
    ): ApiByPopularRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                apiRemoteKeysDao.getRemoteKeys(id = unsplashImage.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieByPopular>
    ): ApiByPopularRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                apiRemoteKeysDao.getRemoteKeys(id = unsplashImage.id.toString())
            }
    }
}