package com.example.moviesdb.data.state

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object CreateRequestTokenSuccess : LoginState()
    object AuthenticationSuccess : LoginState()
    object CreateSessionIDSuccess : LoginState()
    data class Error(val message: String) : LoginState()
}