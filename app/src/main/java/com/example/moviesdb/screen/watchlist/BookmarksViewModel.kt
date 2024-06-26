package com.example.moviesdb.screen.watchlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.data.state.WatchlistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val tag = "BookmarksViewModel.kt"

    private val _bookmarkedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val bookmarkedMovies = _bookmarkedMovies

    fun getBookmarkedMovies(sessionID: String, accountID: Int) {
        viewModelScope.launch {
            repository.getWatchListMovies(sessionID = sessionID, accountID = accountID).cachedIn(viewModelScope).collect {
                _bookmarkedMovies.value = it
            }
        }
    }
}