package com.example.moviesdb.data.state

sealed class LogoutState {
    object Initial : LogoutState()
    object Loading : LogoutState()
    object ShowLogoutDialog : LogoutState()
    object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
}