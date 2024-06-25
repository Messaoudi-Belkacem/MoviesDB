package com.example.moviesdb.screen.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.SearchListContent
import com.example.moviesdb.screen.home.LoadingCircle

@OptIn(ExperimentalPagingApi::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {

    val searchQuery by searchViewModel.searchQuery
    val searchedImages = searchViewModel.searchedMovie.collectAsLazyPagingItems()

    val tag = "SearchScreen.kt"

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(
                    "Search",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Rounded.Camera,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                SearchWidget(
                    text = searchQuery,
                    onTextChange = {
                        searchViewModel.updateSearchQuery(query = it)
                    },
                    onSearchClicked = {
                        searchViewModel.searchMovies(query = it)
                    }
                )
                when (searchedImages.loadState.refresh) {
                    is LoadState.Loading -> {
                        LoadingCircle()
                    }

                    is LoadState.Error -> {
                        Button(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "RETRY",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        }
                        Toast.makeText(
                            LocalContext.current,
                            "Unexpected error",
                            Toast.LENGTH_SHORT
                        ).show()
                        (searchedImages.loadState.refresh as LoadState.Error).error.message?.let {
                            Log.d(
                                tag,
                                it
                            )
                        }
                        (searchedImages.loadState.refresh as LoadState.Error).error.printStackTrace()
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(1f),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            contentPadding = PaddingValues(24.dp)
                        ) {
                            items(count = searchedImages.itemCount) { index ->
                                val item = searchedImages[index]
                                if (item != null) {
                                    SearchListContent(movie = item, navController = navController, sharedViewModel = sharedViewModel)
                                } else {
                                    Log.d("SearchScreen.kt", "item number $index is null")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}