package com.example.moviesdb

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val tag: String = "SharedViewModel.kt"

    private val _selectedMovie: MutableState<Movie?> = mutableStateOf(null)
    val selectedMovie: MutableState<Movie?> = _selectedMovie

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



}
