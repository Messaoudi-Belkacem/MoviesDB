package com.example.moviesdb.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming
import com.example.moviesdb.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "HomeViewModel.kt"

    private val _movieByDiscoverFlow = MutableStateFlow<PagingData<MovieByDiscover>>(PagingData.empty())
    val movieByDiscoverFlow: StateFlow<PagingData<MovieByDiscover>> = _movieByDiscoverFlow.asStateFlow()

    private val _movieByNowPlayingFlow = MutableStateFlow<PagingData<MovieByNowPlaying>>(PagingData.empty())
    val movieByNowPlayingFlow: StateFlow<PagingData<MovieByNowPlaying>> = _movieByNowPlayingFlow.asStateFlow()

    private val _movieByPopularFlow = MutableStateFlow<PagingData<MovieByPopular>>(PagingData.empty())
    val movieByPopularFlow: StateFlow<PagingData<MovieByPopular>> = _movieByPopularFlow.asStateFlow()

    private val _movieByTopRatedFlow = MutableStateFlow<PagingData<MovieByTopRated>>(PagingData.empty())
    val movieByTopRatedFlow: StateFlow<PagingData<MovieByTopRated>> = _movieByTopRatedFlow.asStateFlow()

    private val _movieByUpcomingFlow = MutableStateFlow<PagingData<MovieByUpcoming>>(PagingData.empty())
    val movieByUpcomingFlow: StateFlow<PagingData<MovieByUpcoming>> = _movieByUpcomingFlow.asStateFlow()

    fun getMoviesByDiscover() {
        viewModelScope.launch {
            try {
                repository.getAllMoviesByDiscover()
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _movieByDiscoverFlow.value = pagingData
                        Log.d(tag, "response was successful for movie by discover")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    fun getMoviesByNowPlaying() {
        viewModelScope.launch {
            try {
                repository.getAllMoviesByNowPlaying()
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _movieByNowPlayingFlow.value = pagingData
                        Log.d(tag, "response was successful  for movie by now playing")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    fun getMoviesByPopular() {
        viewModelScope.launch {
            try {
                repository.getAllMoviesByPopular()
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _movieByPopularFlow.value = pagingData
                        Log.d(tag, "response was successful")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    fun getMoviesByTopRated() {
        viewModelScope.launch {
            try {
                repository.getAllMoviesByTopRated()
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _movieByTopRatedFlow.value = pagingData
                        Log.d(tag, "response was successful")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    fun getMoviesByUpcoming() {
        viewModelScope.launch {
            try {
                repository.getAllMoviesByUpcoming()
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _movieByUpcomingFlow.value = pagingData
                        Log.d(tag, "response was successful")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    fun convertMovieByDiscoverToMovie(movieByDiscover: MovieByDiscover): Movie {
        return Movie(
            id = movieByDiscover.id,
            title = movieByDiscover.title,
            overview = movieByDiscover.overview,
            posterPath = movieByDiscover.posterPath,
            backdropPath = movieByDiscover.backdropPath,
            releaseDate = movieByDiscover.releaseDate,
            genreIds = movieByDiscover.genreIds,
            voteAverage = movieByDiscover.voteAverage,
            voteCount = movieByDiscover.voteCount
        )
    }

    fun convertMovieByNowPlayingToMovie(movieByNowPlaying: MovieByNowPlaying): Movie {
        return Movie(
            id = movieByNowPlaying.id,
            title = movieByNowPlaying.title,
            overview = movieByNowPlaying.overview,
            posterPath = movieByNowPlaying.posterPath,
            backdropPath = movieByNowPlaying.backdropPath,
            releaseDate = movieByNowPlaying.releaseDate,
            genreIds = movieByNowPlaying.genreIds,
            voteAverage = movieByNowPlaying.voteAverage,
            voteCount = movieByNowPlaying.voteCount
        )
    }

    fun convertMovieByPopularToMovie(movieByPopular: MovieByPopular): Movie {
        return Movie(
            id = movieByPopular.id,
            title = movieByPopular.title,
            overview = movieByPopular.overview,
            posterPath = movieByPopular.posterPath,
            backdropPath = movieByPopular.backdropPath,
            releaseDate = movieByPopular.releaseDate,
            genreIds = movieByPopular.genreIds,
            voteAverage = movieByPopular.voteAverage,
            voteCount = movieByPopular.voteCount
        )
    }

    fun convertMovieByTopRatedToMovie(movieByTopRated: MovieByTopRated): Movie {
        return Movie(
            id = movieByTopRated.id,
            title = movieByTopRated.title,
            overview = movieByTopRated.overview,
            posterPath = movieByTopRated.posterPath,
            backdropPath = movieByTopRated.backdropPath,
            releaseDate = movieByTopRated.releaseDate,
            genreIds = movieByTopRated.genreIds,
            voteAverage = movieByTopRated.voteAverage,
            voteCount = movieByTopRated.voteCount
        )
    }

    fun convertMovieByUpcomingToMovie(movieByUpcoming: MovieByUpcoming): Movie {
        return Movie(
            id = movieByUpcoming.id,
            title = movieByUpcoming.title,
            overview = movieByUpcoming.overview,
            posterPath = movieByUpcoming.posterPath,
            backdropPath = movieByUpcoming.backdropPath,
            releaseDate = movieByUpcoming.releaseDate,
            genreIds = movieByUpcoming.genreIds,
            voteAverage = movieByUpcoming.voteAverage,
            voteCount = movieByUpcoming.voteCount
        )
    }
}
