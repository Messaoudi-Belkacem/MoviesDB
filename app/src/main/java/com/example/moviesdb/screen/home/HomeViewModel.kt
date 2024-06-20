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
                        Log.d(tag, "response was successful")
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


}
