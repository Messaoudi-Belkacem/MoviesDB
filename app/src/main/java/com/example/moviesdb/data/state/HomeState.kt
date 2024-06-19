package com.example.moviesdb.data.state

import androidx.paging.PagingData
import com.example.moviesdb.data.model.Movie
import kotlinx.coroutines.flow.Flow

sealed class HomeState {
    object Initial : HomeState()
    object Loading : HomeState()
    data class Success(val movies: Flow<PagingData<Movie>>) : HomeState()
    data class Error(val message: String) : HomeState()
}