package com.example.moviesdb

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.User
import com.example.moviesdb.data.model.request.DeleteSessionRequest
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.data.state.LogoutState
import com.example.moviesdb.navigation.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "SharedViewModel"

    private val _selectedMovie: MutableState<Movie?> = mutableStateOf(null)
    val selectedMovie: State<Movie?> get() = _selectedMovie

    private val _condition = MutableStateFlow(true)
    val condition: StateFlow<Boolean> get() = _condition

    private val _startDestination = mutableStateOf(Graph.AUTHENTICATION)
    val startDestination: State<String> get() = _startDestination

    private val _requestToken = MutableStateFlow("")
    val requestToken: StateFlow<String> get() = _requestToken

    private val _approved = MutableStateFlow(false)
    val approved: StateFlow<Boolean> get() = _approved

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private val _sessionID = MutableStateFlow("")
    val sessionID: StateFlow<String> get() = _sessionID

    private val _logoutState = mutableStateOf<LogoutState>(LogoutState.Initial)
    val logoutState: State<LogoutState> get() = _logoutState

    init {
        Log.d(tag, "init block started executing")
        viewModelScope.launch {
            initializeSession()
        }
    }

    private suspend fun initializeSession() {
        val sessionID = repository.getSessionIDFromDatastore()
        if (sessionID.isNullOrEmpty()) {
            Log.d(tag, "sessionID is null or empty, sessionID: $sessionID")
        } else Log.d(tag, "getSessionIDFromDatastore executed successfully, sessionID: $sessionID")
        if (!sessionID.isNullOrEmpty()) {
            _startDestination.value = Graph.HOME
            _sessionID.value = sessionID
            getUser()
        } else {
            _startDestination.value = Graph.AUTHENTICATION
        }

        delay(Duration.ofMillis(1000))
        _condition.value = false
    }

    fun setRequestToken(requestToken: String) {
        _requestToken.value = requestToken
        Log.d(tag, "Request token set: $requestToken")
    }

    fun setApproved(approved: Boolean) {
        _approved.value = approved
        Log.d(tag, "Approved status set: $approved")
    }

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun setSessionID(sessionID: String) {
        _sessionID.value = sessionID
        Log.d(tag, "Session ID set: $sessionID")
    }

    fun getUser() {
        Log.d(tag, "getUser called")
        viewModelScope.launch {
            if (_user.value == null) {
                Log.d(tag, "User details do not exist and are going to be fetched from the api")
                fetchUserDetails()
            } else {
                Log.d(tag, "User details already exist: ${_user.value}")
            }
        }
    }

    private suspend fun fetchUserDetails() {
        val result = repository.getAccountDetails(_sessionID.value)
        result.onSuccess { response ->
            Log.d(tag, "User account details fetched successfully: $response")
            _user.value = response
        }.onFailure { exception ->
            Log.e(tag, "Error fetching user details: ${exception.message}")
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            _logoutState.value = LogoutState.Loading
            val deleteSessionRequest = DeleteSessionRequest(_sessionID.value)
            val result = repository.deleteSession(deleteSessionRequest)
            result.onSuccess { response ->
                if (response.success) {
                    Log.d(tag, "Session deleted successfully from API")
                    repository.saveSessionIDToDatastore("")
                    Log.d(tag, "Session deleted successfully from datastore")
                    _logoutState.value = LogoutState.Success
                } else {
                    Log.e(tag, "Failed to delete session on API")
                    _logoutState.value = LogoutState.Error("Session deletion failed")
                }
            }.onFailure { exception ->
                Log.e(tag, "Error deleting session: ${exception.message}")
                _logoutState.value = LogoutState.Error("Session deletion request failed")
            }
        }
    }

    fun setLogoutState(logoutState: LogoutState) {
        _logoutState.value = logoutState
    }
}
