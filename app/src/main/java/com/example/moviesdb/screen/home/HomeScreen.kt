package com.example.moviesdb.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.screen.common.MovieItem

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        homeViewModel.searchMovie()
    }

    val discoveredMovies = homeViewModel.movieFlow.collectAsLazyPagingItems()
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
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                is LoadState.Error -> {
                    Button(
                        onClick = { homeViewModel.searchMovie() },
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
                            .fillMaxWidth(1f)
                    ) {
                        items(count = discoveredMovies.itemCount) { index ->
                            val item = discoveredMovies[index]
                            if (item != null) {
                                MovieItem(movie = item)
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
                        text = { Text(text = title, fontFamily = fontFamily) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                is 0 -> {

                }
                is 1 -> {

                }
                is 2 -> {

                }
                is 3 -> {

                }
            }
        }
    }
}

@Composable
fun TabContent(lazyPagingItems: LazyPagingItems<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            if (item != null) {
                MovieItem(movie = item)
            } else {
                Log.d("HomeScreen.kt", "item number $index is null")
            }
        }
    }
}