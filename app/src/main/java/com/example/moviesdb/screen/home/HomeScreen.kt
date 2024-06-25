package com.example.moviesdb.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesdb.R
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.data.model.MovieByNowPlaying
import com.example.moviesdb.data.model.MovieByPopular
import com.example.moviesdb.data.model.MovieByTopRated
import com.example.moviesdb.data.model.MovieByUpcoming
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.MovieItem

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(Unit) {
        homeViewModel.getMoviesByDiscover()
        homeViewModel.getMoviesByNowPlaying()
        homeViewModel.getMoviesByPopular()
        homeViewModel.getMoviesByTopRated()
        homeViewModel.getMoviesByUpcoming()
    }

    val modifierForRowItem = true

    val discoveredMovies = homeViewModel.movieByDiscoverFlow.collectAsLazyPagingItems()
    val nowPlayingMovies = homeViewModel.movieByNowPlayingFlow.collectAsLazyPagingItems()
    val popularMovies = homeViewModel.movieByPopularFlow.collectAsLazyPagingItems()
    val topRatedMovies = homeViewModel.movieByTopRatedFlow.collectAsLazyPagingItems()
    val upcomingMovies = homeViewModel.movieByUpcomingFlow.collectAsLazyPagingItems()
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
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(count = discoveredMovies.itemCount) { index ->
                            val item = discoveredMovies[index]
                            if (item != null) {
                                val movie = homeViewModel.convertMovieByDiscoverToMovie(item)
                                MovieItem(
                                    movie = movie,
                                    modifier = modifierForRowItem,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel
                                )
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
                                    onClick = { homeViewModel.getMoviesByNowPlaying() },
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
                                TabContent(
                                    nowPlayingLazyPagingItems = nowPlayingMovies,
                                    popularLazyPagingItems = null,
                                    topRatedLazyPagingItems = null,
                                    upcomingLazyPagingItems = null,
                                    homeViewModel = homeViewModel,
                                    tabIndex = tabIndex,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }
                    }
                    1 -> {
                        when (popularMovies.loadState.refresh) {
                            is LoadState.Loading -> {
                                LoadingCircle()
                            }
                            is LoadState.Error -> {
                                Button(
                                    onClick = { homeViewModel.getMoviesByPopular() },
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
                                (popularMovies.loadState.refresh as LoadState.Error).error.message?.let {
                                    Log.d("HomeScreen.kt",
                                        it
                                    )
                                }
                                (discoveredMovies.loadState.refresh as LoadState.Error).error.printStackTrace()
                            }
                            else -> {
                                TabContent(
                                    nowPlayingLazyPagingItems = null,
                                    popularLazyPagingItems = popularMovies,
                                    topRatedLazyPagingItems = null,
                                    upcomingLazyPagingItems = null,
                                    homeViewModel = homeViewModel,
                                    tabIndex = tabIndex,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }
                    }
                    2 -> {
                        when (topRatedMovies.loadState.refresh) {
                            is LoadState.Loading -> {
                                LoadingCircle()
                            }
                            is LoadState.Error -> {
                                Button(
                                    onClick = { homeViewModel.getMoviesByTopRated() },
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
                                (popularMovies.loadState.refresh as LoadState.Error).error.message?.let {
                                    Log.d("HomeScreen.kt",
                                        it
                                    )
                                }
                                (discoveredMovies.loadState.refresh as LoadState.Error).error.printStackTrace()
                            }
                            else -> {
                                TabContent(
                                    nowPlayingLazyPagingItems = null,
                                    popularLazyPagingItems = null,
                                    topRatedLazyPagingItems = topRatedMovies,
                                    upcomingLazyPagingItems = null,
                                    homeViewModel = homeViewModel,
                                    tabIndex = tabIndex,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }
                    }
                    3 -> {
                        when (upcomingMovies.loadState.refresh) {
                            is LoadState.Loading -> {
                                LoadingCircle()
                            }
                            is LoadState.Error -> {
                                Button(
                                    onClick = { homeViewModel.getMoviesByUpcoming() },
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
                                (popularMovies.loadState.refresh as LoadState.Error).error.message?.let {
                                    Log.d("HomeScreen.kt",
                                        it
                                    )
                                }
                                (discoveredMovies.loadState.refresh as LoadState.Error).error.printStackTrace()
                            }
                            else -> {
                                TabContent(
                                    nowPlayingLazyPagingItems = null,
                                    popularLazyPagingItems = null,
                                    topRatedLazyPagingItems = null,
                                    upcomingLazyPagingItems = upcomingMovies,
                                    homeViewModel = homeViewModel,
                                    tabIndex = tabIndex,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun TabContent(
    nowPlayingLazyPagingItems: LazyPagingItems<MovieByNowPlaying>?,
    popularLazyPagingItems: LazyPagingItems<MovieByPopular>?,
    topRatedLazyPagingItems: LazyPagingItems<MovieByTopRated>?,
    upcomingLazyPagingItems: LazyPagingItems<MovieByUpcoming>?,
    homeViewModel: HomeViewModel,
    tabIndex: Int,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)

    ) {
        when (tabIndex) {
            0 -> {
                items(count = nowPlayingLazyPagingItems!!.itemCount) { index ->
                    val item = nowPlayingLazyPagingItems[index]
                    if (item != null) {
                        val movie = homeViewModel.convertMovieByNowPlayingToMovie(item)
                        MovieItem(
                            movie = movie,
                            modifier = null,
                            navController = navController,
                            sharedViewModel = sharedViewModel
                            )
                    } else {
                        Log.d("HomeScreen.kt", "item number $index is null")
                    }
                }
            }
            1 -> {
                items(count = popularLazyPagingItems!!.itemCount) { index ->
                    val item = popularLazyPagingItems[index]
                    if (item != null) {
                        val movie = homeViewModel.convertMovieByPopularToMovie(item)
                        MovieItem(
                            movie = movie,
                            modifier = null,
                            navController = navController,
                            sharedViewModel = sharedViewModel
                        )
                    } else {
                        Log.d("HomeScreen.kt", "item number $index is null")
                    }
                }
            }
            2 -> {
                items(count = topRatedLazyPagingItems!!.itemCount) { index ->
                    val item = topRatedLazyPagingItems[index]
                    if (item != null) {
                        val movie = homeViewModel.convertMovieByTopRatedToMovie(item)
                        MovieItem(
                            movie = movie,
                            modifier = null,
                            navController = navController,
                            sharedViewModel = sharedViewModel
                            )
                    } else {
                        Log.d("HomeScreen.kt", "item number $index is null")
                    }
                }
            }
            3 -> {
                items(count = upcomingLazyPagingItems!!.itemCount) { index ->
                    val item = upcomingLazyPagingItems[index]
                    if (item != null) {
                        val movie = homeViewModel.convertMovieByUpcomingToMovie(item)
                        MovieItem(
                            movie = movie,
                            modifier = null,
                            navController = navController,
                            sharedViewModel = sharedViewModel
                        )
                    } else {
                        Log.d("HomeScreen.kt", "item number $index is null")
                    }
                }
            }
        }

    }
}

@Composable
fun LoadingCircle() {
    Box(
        modifier = Modifier.height(256.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}