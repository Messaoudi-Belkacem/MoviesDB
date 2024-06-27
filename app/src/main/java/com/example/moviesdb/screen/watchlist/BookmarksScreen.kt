package com.example.moviesdb.screen.watchlist

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesdb.R
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.data.state.LogoutState
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.SearchListContent
import com.example.moviesdb.screen.home.LoadingCircle

@OptIn(ExperimentalPagingApi::class)
@Composable
fun BookmarksScreen(
    navController: NavHostController,
    bookmarksViewModel: BookmarksViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
    ) {

    val logoutState by sharedViewModel.logoutState
    val bookmarkedMovies = bookmarksViewModel.bookmarkedMovies.collectAsLazyPagingItems()
    val tag = "BookmarksScreen.kt"

    LaunchedEffect(Unit) {
        val user = sharedViewModel.user.value
        val sessionID = sharedViewModel.sessionID.value
        if (user != null) {
            bookmarksViewModel.getBookmarkedMovies(sessionID = sessionID, accountID = user.id)
            Log.d(tag, "sessionID exists and user is not null")
            Log.d(tag, "sessionID : $sessionID")
            Log.d(tag, "accountID : ${user.id}")
        } else {
            Log.d(tag, "user is null")
            Log.d(tag, "sessionID : $sessionID")
        }
    }

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
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(
                    "WatchList",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                IconButton(onClick = {
                    sharedViewModel.deleteSession()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Logout,
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
                when (logoutState) {
                    is LogoutState.Initial -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        when (bookmarkedMovies.loadState.refresh) {
                            is LoadState.Loading -> {
                                LoadingCircle()
                            }
                            is LoadState.Error -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(
                                        onClick = { /*TODO*/ },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "RETRY",
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize
                                        )
                                    }
                                }
                                Toast.makeText(
                                    LocalContext.current,
                                    "Unexpected error",
                                    Toast.LENGTH_SHORT
                                ).show()
                                (bookmarkedMovies.loadState.refresh as LoadState.Error).error.message?.let {
                                    Log.d(tag, it)
                                }
                                (bookmarkedMovies.loadState.refresh as LoadState.Error).error.printStackTrace()
                            }
                            else -> {
                                if (bookmarkedMovies.itemCount != 0) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth(1f),
                                        verticalArrangement = Arrangement.spacedBy(24.dp),
                                        contentPadding = PaddingValues(24.dp)
                                    ) {
                                        items(count = bookmarkedMovies.itemCount) { index ->
                                            val item = bookmarkedMovies[index]
                                            if (item != null) {
                                                SearchListContent(movie = item, navController = navController, sharedViewModel = sharedViewModel)
                                            } else {
                                                Log.d(tag, "item number $index is null")
                                            }
                                        }
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.magic_box_drawable),
                                                contentDescription = null
                                            )
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Text(
                                                text = "There is no movie yet!",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFFEBEBEF),
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Find your movie by Type title,\n categories, years, etc ",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color(0xFF92929D),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is LogoutState.Error -> {
                        /* TODO create a popup that apologizes foe not being able to logout */
                        sharedViewModel.setLogoutState(logoutState = LogoutState.Initial)
                    }
                    is LogoutState.Loading -> {
                        LoadingCircle()
                    }
                    is LogoutState.Success -> {
                        navController.popBackStack()
                        navController.navigate(Graph.AUTHENTICATION)
                    }
                }
            }
        }
    )
}