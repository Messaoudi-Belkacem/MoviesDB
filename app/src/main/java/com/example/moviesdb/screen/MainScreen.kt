package com.example.moviesdb.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.screen.common.BottomNavigationBar
import com.example.moviesdb.navigation.HomeNavGraph

@OptIn(ExperimentalPagingApi::class)
@Composable
fun MainScreen(navHostController: NavHostController = rememberNavController(), sharedViewModel: SharedViewModel) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController = navHostController)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            HomeNavGraph(navController = navHostController, sharedViewModel = sharedViewModel)
        }
    }
}