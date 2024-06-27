package com.example.moviesdb.data.state

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    object CreateRequestTokenSuccess : LoginState()
    object AuthenticationSuccess : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class LoginSubState {
    object Initial : LoginSubState()
    object CreateRequestTokenSuccess : LoginSubState()
    data class Error(val message: String) : LoginSubState()
}