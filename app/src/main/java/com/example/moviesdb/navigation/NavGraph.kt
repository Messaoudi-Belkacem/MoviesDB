package com.example.moviesdb.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviesdb.screen.HomeScreen
import com.example.moviesdb.screen.MainScreen
import com.example.moviesdb.screen.SearchScreen
import com.example.moviesdb.screen.WatchListScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    val time = 500
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(animationSpec = tween(time), initialOffsetX = {fullWidth ->  
            -fullWidth
        }) },
        exitTransition = { slideOutHorizontally(animationSpec = tween(time), targetOffsetX = {fullWidth ->
            fullWidth
        }) },
        popEnterTransition = { slideInHorizontally(animationSpec = tween(time), initialOffsetX = {fullWidth ->
            -fullWidth
        }) },
        popExitTransition = { slideOutHorizontally(animationSpec = tween(time), targetOffsetX = {fullWidth ->
            fullWidth
        }) }
    ) {
        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen()
        }
        composable(
            route = Screen.Search.route,
        ) {
            SearchScreen()
        }
        composable(
            route = Screen.WatchList.route,
        ) {
            WatchListScreen()
        }
    }
}