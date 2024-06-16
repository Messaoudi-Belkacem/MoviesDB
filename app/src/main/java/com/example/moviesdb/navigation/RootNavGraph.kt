package com.example.moviesdb.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviesdb.screen.HomeScreen
import com.example.moviesdb.screen.MainScreen

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController
) {
    val time = 500
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION,
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
        authNavGraph(navHostController)
        composable(route = Graph.HOME) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "authentication_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}