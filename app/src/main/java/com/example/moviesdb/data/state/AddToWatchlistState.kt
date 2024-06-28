package com.example.moviesdb.data.state

sealed class AddToWatchlistState {
    object Initial : AddToWatchlistState()
    object Loading : AddToWatchlistState()
    data class Success(val message: String) : AddToWatchlistState()
    data class Error(val message: String) : AddToWatchlistState()
}