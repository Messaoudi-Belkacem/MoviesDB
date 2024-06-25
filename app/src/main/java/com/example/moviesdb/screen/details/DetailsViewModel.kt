package com.example.moviesdb.screen.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.data.model.CastMember
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.MovieByDiscover
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming
import com.example.moviesdb.data.model.Review
import com.example.moviesdb.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "DetailsViewModel.kt"

    private val _reviewsFlow = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviewsFlow: StateFlow<PagingData<Review>> = _reviewsFlow.asStateFlow()

    private val _castFlow = MutableStateFlow<PagingData<CastMember>>(PagingData.empty())
    val castFlow: StateFlow<PagingData<CastMember>> = _castFlow.asStateFlow()

    fun getReviews(movieID: Int) {
        viewModelScope.launch {
            try {
                repository.getReviews(movieID)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _reviewsFlow.value = pagingData
                        Log.d(tag, "response was successful for getting reviews")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }

    fun getCast(movieID: Int) {
        viewModelScope.launch {
            try {
                repository.getCast(movieID)
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _castFlow.value = pagingData
                        Log.d(tag, "response was successful for getting cast")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }
}
