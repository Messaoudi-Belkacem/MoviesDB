package com.example.moviesdb.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesdb.data.local.MovieDatabase
import com.example.moviesdb.data.model.CastMember
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming
import com.example.moviesdb.data.model.Review
import com.example.moviesdb.data.model.User
import com.example.moviesdb.data.model.request.AddToWatchlistRequest
import com.example.moviesdb.data.model.request.CreateSessionRequest
import com.example.moviesdb.data.model.response.CreateSessionResponse
import com.example.moviesdb.data.model.request.DeleteSessionRequest
import com.example.moviesdb.data.model.response.AddToWatchlistResponse
import com.example.moviesdb.data.model.response.RequestTokenResponse
import com.example.moviesdb.data.model.response.DeleteSessionResponse
import com.example.moviesdb.data.paging.ApiDiscoverRemoteMediator
import com.example.moviesdb.data.paging.ApiNowPlayingRemoteMediator
import com.example.moviesdb.data.paging.ApiPopularRemoteMediator
import com.example.moviesdb.data.paging.ApiTopRatedRemoteMediator
import com.example.moviesdb.data.paging.ApiUpcomingRemoteMediator
import com.example.moviesdb.data.paging.CastPagingSource
import com.example.moviesdb.data.paging.ReviewsPagingSource
import com.example.moviesdb.data.paging.SearchPagingSource
import com.example.moviesdb.data.paging.WatchlistPagingSource
import com.example.moviesdb.data.remote.MovieApi
import com.example.moviesdb.util.Constants.Companion.ITEMS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase,
    private val dataStore: DataStore<Preferences>
) {

    private val tag = "repository"
    private val sessionIDKey = stringPreferencesKey("session_id")

    suspend fun getSessionIDFromDatastore(): String? {
        Log.d(tag, "getSessionID is called")
        val sessionIdFlow = dataStore.data.first()
        return sessionIdFlow[sessionIDKey]
    }

    suspend fun saveSessionIDToDatastore(sessionID: String) {
        dataStore.edit { preferences ->
            preferences[this.sessionIDKey] = sessionID
        }
    }

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

    fun getAllMoviesByPopular(): Flow<PagingData<MovieByPopular>> {
        val pagingSourceFactory = { movieDatabase.apiMovieByPopularDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ApiPopularRemoteMediator(
                movieApi = movieApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllMoviesByTopRated(): Flow<PagingData<MovieByTopRated>> {
        val pagingSourceFactory = { movieDatabase.apiMovieByTopRatedDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ApiTopRatedRemoteMediator(
                movieApi = movieApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getAllMoviesByUpcoming(): Flow<PagingData<MovieByUpcoming>> {
        val pagingSourceFactory = { movieDatabase.apiMovieByUpcomingDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ApiUpcomingRemoteMediator(
                movieApi = movieApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun getReviews(movieID: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                ReviewsPagingSource(movieApi = movieApi, movieID = movieID)
            }
        ).flow
    }

    fun getCast(movieID: Int): Flow<PagingData<CastMember>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                CastPagingSource(movieApi = movieApi, movieID = movieID)
            }
        ).flow
    }

    fun searchMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(movieApi = movieApi, query = query)
            }
        ).flow
    }

    suspend fun getRequestToken(): Result<RequestTokenResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getRequestToken()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun createSession(createSessionRequest: CreateSessionRequest): Result<CreateSessionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.createSession(rawBody = createSessionRequest)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun deleteSession(deleteSessionRequest: DeleteSessionRequest): Result<DeleteSessionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.deleteSession(sessionID = deleteSessionRequest.sessionID)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getAccountDetails(sessionID: String): Result<User> {
        Log.d(tag, "getAccountDetails is called with sessionID: $sessionID")
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getAccountDetails(sessionID = sessionID)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun getWatchListMovies(sessionID: String, accountID: Int): Flow<PagingData<Movie>> {
        return Pager(
                config = PagingConfig(pageSize = ITEMS_PER_PAGE),
        pagingSourceFactory = {
            WatchlistPagingSource(movieApi = movieApi, sessionID = sessionID, accountID = accountID)
        }
        ).flow
    }

    suspend fun addToWatchlist(accountID: Int, sessionID: String, request: AddToWatchlistRequest): Result<AddToWatchlistResponse> {
        return try {
            val response = movieApi.addToWatchlist(accountID, sessionID, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}