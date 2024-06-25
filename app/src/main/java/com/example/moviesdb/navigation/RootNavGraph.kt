package com.example.moviesdb.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.screen.MainScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
    startDestination: String
) {
    val time = 500
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
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
        authNavGraph(navHostController, sharedViewModel)
        composable(route = Graph.HOME) {
            MainScreen(sharedViewModel = sharedViewModel)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "authentication_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}