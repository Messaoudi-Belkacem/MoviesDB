package com.example.moviesdb.screen.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.model.request.CreateSessionRequest
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "LoginViewModel.kt"

    private val _requestToken = MutableStateFlow("")
    val requestToken: StateFlow<String> get() = _requestToken

    private val _loginState = mutableStateOf<LoginState>(LoginState.Initial)
    val loginState: State<LoginState> = _loginState

    fun getRequestToken() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = repository.getRequestToken()
            delay(4000)
            result.onSuccess { response ->
                // Handle the successful response
                Log.d(tag, "Success: $response")
                setRequestToken(requestToken = response.requestToken)
                _loginState.value = LoginState.CreateRequestTokenSuccess
            }.onFailure { exception ->
                // Handle the error
                Log.d(tag, "Error: ${exception.message}")
                _loginState.value = LoginState.Initial
            }
        }
    }

    fun askForUserPermission(context: Context) {
        viewModelScope.launch {
            val result = requestToken.value
            val url = "https://www.themoviedb.org/authenticate/$result?redirect_to=movies://callback"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }

    fun createSessionId(requestToken: String, approved: Boolean) {
        // Coroutine scope to call the API
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val createSessionRequest = CreateSessionRequest(requestToken = requestToken, approved = approved)
            val sessionResponse = repository.createSession(createSessionRequest)
            if (sessionResponse.isSuccess) {
                // Handle successful session creation
                _loginState.value = LoginState.CreateSessionIDSuccess
                Log.e(tag, sessionResponse.toString())
            } else {
                Log.e("API_ERROR", "Session creation failed")
            }
        }
    }

    fun setRequestToken(requestToken: String) {
        _requestToken.value = requestToken
    }

    fun setLoginState(loginState: LoginState) {
        _loginState.value = loginState
    }
}
