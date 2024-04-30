package com.example.moviesdb.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moviesdb.item.BottomNavigationBar
import com.example.moviesdb.navigation.Screen
import com.example.moviesdb.navigation.SetupNavGraph

@Composable
fun MainScreen(navHostController: NavHostController) {
    val startDestination = Screen.Home.route
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController = navHostController)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SetupNavGraph(
                navHostController = navHostController,
                startDestination = startDestination
            )
        }
    }
}