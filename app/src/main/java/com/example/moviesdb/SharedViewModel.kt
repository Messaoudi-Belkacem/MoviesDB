package com.example.moviesdb

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.repository.Repository
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

    private val tag: String = "SharedViewModel.kt"

    private val _selectedMovie: MutableState<Movie?> = mutableStateOf(null)
    val selectedMovie: MutableState<Movie?> = _selectedMovie

    private val _condition = MutableStateFlow(true)
    val condition: MutableStateFlow<Boolean> = _condition

    private val _startDestination = mutableStateOf(Graph.AUTHENTICATION)
    val startDestination: MutableState<String> = _startDestination

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    private val _requestToken = MutableStateFlow("")
    val requestToken: StateFlow<String> get() = _requestToken

    private val _approved = MutableStateFlow(false)
    val approved: StateFlow<Boolean> get() = _approved

    private val _rawBody = MutableStateFlow("")
    val rawBody: StateFlow<String> get() = _rawBody

    fun setRequestToken(requestToken: String) {
        _requestToken.value = requestToken
        Log.d(tag, requestToken)
    }

    fun setApproved(approved: Boolean) {
        _approved.value = approved
        Log.d(tag, approved.toString())
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
            } else {
                _startDestination.value = Graph.AUTHENTICATION
                Log.d(tag, "condition value is set to false and start destination is set to AUTHENTICATION")
            }
            delay(duration = Duration.ofMillis(1000))
            _condition.value = false
        }
    }
}
