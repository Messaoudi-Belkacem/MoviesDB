package com.example.moviesdb.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesdb.data.local.MovieDatabase
import com.example.moviesdb.data.model.ApiByDiscoverRemoteKeys
import com.example.moviesdb.data.model.ApiByNowPlayingRemoteKeys
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.remote.MovieApi

@ExperimentalPagingApi
class ApiNowPlayingRemoteMediator(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, MovieByNowPlaying>() {

    private val apiByNowPlayingMovieDao = movieDatabase.apiMovieByNowPlayingDao()
    private val apiByNowPlayingRemoteKeysDao = movieDatabase.apiByNowPlayingRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieByNowPlaying>
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

            val response = movieApi.getAllMoviesByNowPlaying(page = currentPage).results
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    apiByNowPlayingMovieDao.deleteAllMovies()
                    apiByNowPlayingRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { movie ->
                    ApiByNowPlayingRemoteKeys(
                        id = movie.id.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                apiByNowPlayingRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                apiByNowPlayingMovieDao.addMovies(movies = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieByNowPlaying>
    ): ApiByNowPlayingRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                apiByNowPlayingRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieByNowPlaying>
    ): ApiByNowPlayingRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                apiByNowPlayingRemoteKeysDao.getRemoteKeys(id = unsplashImage.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieByNowPlaying>
    ): ApiByNowPlayingRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                apiByNowPlayingRemoteKeysDao.getRemoteKeys(id = unsplashImage.id.toString())
            }
    }
}