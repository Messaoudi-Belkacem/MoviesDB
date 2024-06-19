package com.example.moviesdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.screen.BookmarksScreen
import com.example.moviesdb.screen.DetailsScreen
import com.example.moviesdb.screen.SearchScreen
import com.example.moviesdb.screen.home.HomeScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.WatchList.route) {
            BookmarksScreen()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.Details.route
    ) {
        composable(route = Screen.Details.route) {
            DetailsScreen()
        }
    }
}