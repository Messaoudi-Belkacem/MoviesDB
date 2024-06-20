package com.example.moviesdb.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesdb.R
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.screen.common.MovieItem

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        homeViewModel.getMoviesByDiscover()
        homeViewModel.getMoviesByNowPlaying()
    }

    val discoveredMovies = homeViewModel.movieByDiscoverFlow.collectAsLazyPagingItems()
    val nowPlayingMovies = homeViewModel.movieByNowPlayingFlow.collectAsLazyPagingItems()
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Now Playing", "Popular", "Top Rated", "Upcoming")
    val fontFamily = FontFamily(Font(R.font.poppins))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val context = LocalContext.current

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "What do you want to watch?",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(1f),
            contentAlignment = Alignment.Center
        ) {
            when (discoveredMovies.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingCircle()
                }
                is LoadState.Error -> {
                    Button(
                        onClick = { homeViewModel.getMoviesByDiscover() },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "RETRY",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize
                        )
                    }
                    Toast.makeText(
                        context,
                        "Unexpected error",
                        Toast.LENGTH_SHORT
                    ).show()
                    (discoveredMovies.loadState.refresh as LoadState.Error).error.message?.let {
                        Log.d("HomeScreen.kt",
                            it
                        )
                    }
                    (discoveredMovies.loadState.refresh as LoadState.Error).error.printStackTrace()
                }
                else -> {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(count = discoveredMovies.itemCount) { index ->
                            val item = discoveredMovies[index]
                            if (item != null) {
                                val movie = homeViewModel.convertMovieByDiscoverToMovie(item)
                                MovieItem(movie = movie)
                            } else {
                                Log.d("HomeScreen.kt", "item number $index is null")
                            }
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
                selectedTabIndex = tabIndex
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title, fontFamily = fontFamily, fontSize = 12.sp) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(1f),
                contentAlignment = Alignment.Center
            ) {
                when (tabIndex) {
                    0 -> {
                        when (nowPlayingMovies.loadState.refresh) {
                            is LoadState.Loading -> {
                                LoadingCircle()
                            }
                            is LoadState.Error -> {
                                Button(
                                    onClick = { homeViewModel.getMoviesByDiscover() },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "RETRY",
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    "Unexpected error",
                                    Toast.LENGTH_SHORT
                                ).show()
                                (discoveredMovies.loadState.refresh as LoadState.Error).error.message?.let {
                                    Log.d("HomeScreen.kt",
                                        it
                                    )
                                }
                                (discoveredMovies.loadState.refresh as LoadState.Error).error.printStackTrace()
                            }
                            else -> {
                                TabContent(lazyPagingItems = nowPlayingMovies, homeViewModel = homeViewModel)
                            }
                        }
                    }
                    1 -> {
                        Text(
                            text = "Popular",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily
                        )
                    }
                    2 -> {
                        Text(
                            text = "Top Rated",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily
                        )
                    }
                    3 -> {
                        Text(
                            text = "Upcoming",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TabContent(
    lazyPagingItems: LazyPagingItems<MovieByNowPlaying>,
    homeViewModel: HomeViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)

    ) {
        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            if (item != null) {
                val movie = homeViewModel.convertMovieByNowPlayingToMovie(item)
                MovieItem(movie = movie)
            } else {
                Log.d("HomeScreen.kt", "item number $index is null")
            }
        }
    }
}

@Composable
fun LoadingCircle() {
    CircularProgressIndicator(
        modifier = Modifier.size(40.dp),
        color = MaterialTheme.colorScheme.primary,
    )
}