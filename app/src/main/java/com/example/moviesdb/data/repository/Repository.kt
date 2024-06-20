package com.example.moviesdb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesdb.data.local.MovieDatabase
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.paging.ApiDiscoverRemoteMediator
import com.example.moviesdb.data.paging.ApiNowPlayingRemoteMediator
import com.example.moviesdb.data.remote.MovieApi
import com.example.moviesdb.util.Constants.Companion.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) {

    fun getAllMoviesByDiscover(): Flow<PagingData<MovieByDiscover>> {
        val pagingSourceFactory = { movieDatabase.apiMovieByDiscoverDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ApiDiscoverRemoteMediator(
                movieApi = movieApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllMoviesByNowPlaying(): Flow<PagingData<MovieByNowPlaying>> {
        val pagingSourceFactory = { movieDatabase.apiMovieByNowPlayingDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ApiNowPlayingRemoteMediator(
                movieApi = movieApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}