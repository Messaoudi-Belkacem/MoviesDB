package com.example.moviesdb.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviesdb.item.BottomNavigationBar
import com.example.moviesdb.navigation.BottomBarScreen
import com.example.moviesdb.navigation.HomeNavGraph
import com.example.moviesdb.navigation.RootNavigationGraph

@Composable
fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController = navHostController)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            HomeNavGraph(navController = navHostController)
        }
    }
}