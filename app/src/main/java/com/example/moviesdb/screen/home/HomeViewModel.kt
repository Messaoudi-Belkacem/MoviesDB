package com.example.moviesdb.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.data.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "HomeViewModel.kt"

    private val _movieFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movieFlow: StateFlow<PagingData<Movie>> = _movieFlow.asStateFlow()

    fun searchMovie() {
        viewModelScope.launch {
            try {
                repository.getAllMovies()
                    .cachedIn(viewModelScope)
                    .catch { e ->
                        Log.d(tag, "An unexpected error occurred.", e)
                    }
                    .collectLatest { pagingData ->
                        _movieFlow.value = pagingData
                        Log.d(tag, "response was successful")
                    }
            } catch (e: ConnectException) {
                Log.d(tag, "Failed to connect to the server. Please check your internet connection.")
            } catch (e: Exception) {
                Log.d(tag, "An unexpected error occurred.", e)
            }
        }
    }
}
