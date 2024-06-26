package com.example.moviesdb.data.state

sealed class WatchlistState {
    object Initial : WatchlistState()
    object Loading : WatchlistState()
    object GetUserSuccess : WatchlistState()
    object Success : WatchlistState()
    data class Error(val message: String) : WatchlistState()
}