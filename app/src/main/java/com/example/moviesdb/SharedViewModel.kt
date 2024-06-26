package com.example.moviesdb

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.User
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.navigation.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "SharedViewModel.kt"

    private val _selectedMovie: MutableState<Movie?> = mutableStateOf(null)
    val selectedMovie: MutableState<Movie?> = _selectedMovie

    private val _condition = MutableStateFlow(true)
    val condition: MutableStateFlow<Boolean> = _condition

    private val _startDestination = mutableStateOf(Graph.AUTHENTICATION)
    val startDestination: MutableState<String> = _startDestination

    private val _requestToken = MutableStateFlow("")
    val requestToken: StateFlow<String> get() = _requestToken

    private val _approved = MutableStateFlow(false)
    val approved: StateFlow<Boolean> get() = _approved

    private val _rawBody = MutableStateFlow("")
    val rawBody: StateFlow<String> get() = _rawBody

    private val _user = MutableStateFlow<User?>(null)
    val user = _user

    private val _sessionID = MutableStateFlow("")
    val sessionID: StateFlow<String> get() = _sessionID

    fun setRequestToken(requestToken: String) {
        _requestToken.value = requestToken
        Log.d(tag, requestToken)
    }

    fun setApproved(approved: Boolean) {
        _approved.value = approved
        Log.d(tag, approved.toString())
    }

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    private fun setSessionID(sessionID: String) {
        _sessionID.value = sessionID
        Log.d(tag, "sessionID has been set in the login viewmodel")
    }

    init {
        Log.d(tag, "init block started executing")
        // Because getSessionID is a suspend function
        viewModelScope.launch {
            val sessionID = repository.getSessionID()
            Log.d(tag, "getSessionID executed successfully and sessionID is :$sessionID")
            if (sessionID != null) {
                _startDestination.value = Graph.HOME
                Log.d(tag, "condition value is set to false and start destination is set to GraphHOME")
                _sessionID.value = sessionID
                getUser(sessionID = sessionID)
            } else {
                _startDestination.value = Graph.AUTHENTICATION
                Log.d(tag, "condition value is set to false and start destination is set to AUTHENTICATION")
            }
            delay(duration = Duration.ofMillis(1000))
            _condition.value = false
        }
    }

    fun getUser(sessionID: String) {
        viewModelScope.launch {
            if (_user.value == null) {
                val result = repository.getAccountDetails(sessionID)
                result.onSuccess { response ->
                    // Handle the successful response
                    Log.d(tag, "Success while fetching user account details: $response")
                    _user.value = response
                }.onFailure { exception ->
                    // Handle the error
                    Log.d(tag, "Error: ${exception.message}")
                }
            } else {
                Log.d(tag, "user details already exist : $_user")
            }
        }
    }
}
