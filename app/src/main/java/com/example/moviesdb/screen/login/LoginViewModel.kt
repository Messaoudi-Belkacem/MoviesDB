package com.example.moviesdb.screen.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.model.request.CreateSessionRequest
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.data.state.LoginState
import com.example.moviesdb.data.state.LoginSubState
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

    private val _sessionID = MutableStateFlow("")
    val sessionID: StateFlow<String> get() = _sessionID

    private val _loginState = mutableStateOf<LoginState>(LoginState.Initial)
    val loginState: State<LoginState> = _loginState

    private val _loginSubState = mutableStateOf<LoginSubState>(LoginSubState.Initial)
    val loginSubState: State<LoginSubState> = _loginSubState

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

    suspend fun createSessionId(requestToken: String, approved: Boolean) {
        // Coroutine scope to call the API
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val createSessionRequest = CreateSessionRequest(requestToken = requestToken, approved = approved)
            val sessionResponse = repository.createSession(createSessionRequest)
            if (sessionResponse.isSuccess) {
                // Handle successful session creation
                val createSessionResponse = sessionResponse.getOrNull()
                if (createSessionResponse != null) {
                    setSessionID(sessionID = createSessionResponse.sessionID)
                    Log.d(tag, "createSessionId was successful and response is: $createSessionResponse")
                    _loginSubState.value = LoginSubState.CreateRequestTokenSuccess
                } else {
                    Log.e("API_ERROR", "Session creation failed")
                    _loginState.value = LoginState.Error("Session creation failed")
                }
                Log.e(tag, sessionResponse.toString())
            } else {
                Log.e("API_ERROR", "Session creation failed")
                _loginState.value = LoginState.Error("Session creation failed")
            }
        }
    }

    private fun setRequestToken(requestToken: String) {
        _requestToken.value = requestToken
    }

    private fun setSessionID(sessionID: String) {
        _sessionID.value = sessionID
        Log.d(tag, "sessionID has been set in the login viewmodel : $sessionID")
    }

    fun getSessionID(): String{
        return  _sessionID.value
    }

    fun setLoginState(loginState: LoginState) {
        viewModelScope.launch {
            _loginState.value = loginState
        }
    }

    fun setLoginSubState(loginSubState: LoginSubState) {
        viewModelScope.launch {
            _loginSubState.value = loginSubState
        }
    }

    fun saveSessionIDToDatastore(sessionID: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            repository.saveSessionIDToDatastore(sessionID = sessionID)
            Log.d(tag, "sessionID has been saved to the data store : $sessionID")
        }
    }
}
