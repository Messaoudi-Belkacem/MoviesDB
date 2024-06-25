package com.example.moviesdb.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesdb.data.local.MovieDatabase
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.remote.MovieApi

@ExperimentalPagingApi
class ApiDiscoverRemoteMediator(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, MovieByDiscover>() {

    private val apiMovieByDiscoverDao = movieDatabase.apiMovieByDiscoverDao()
    private val apiByDiscoverRemoteKeysDao = movieDatabase.apiByDiscoverRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieByDiscover>
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

            val response = movieApi.getAllMoviesByDiscover(page = currentPage).results
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    apiMovieByDiscoverDao.deleteAllMovies()
                    apiByDiscoverRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { movie ->
                    ApiByDiscoverRemoteKeys(
                        id = movie.id.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                apiByDiscoverRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                apiMovieByDiscoverDao.addMovies(movies = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieByDiscover>
    ): ApiByDiscoverRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                apiByDiscoverRemoteKeysDao.getRemoteKeys(id = it.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieByDiscover>
    ): ApiByDiscoverRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                apiByDiscoverRemoteKeysDao.getRemoteKeys(id = it.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieByDiscover>
    ): ApiByDiscoverRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                apiByDiscoverRemoteKeysDao.getRemoteKeys(id = it.id.toString())
            }
    }
}