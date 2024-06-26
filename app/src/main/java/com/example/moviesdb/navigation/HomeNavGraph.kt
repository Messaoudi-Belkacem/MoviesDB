package com.example.moviesdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.screen.watchlist.BookmarksScreen
import com.example.moviesdb.screen.details.DetailsScreen
import com.example.moviesdb.screen.search.SearchScreen
import com.example.moviesdb.screen.home.HomeScreen

@OptIn(ExperimentalPagingApi::class)
@Composable
fun HomeNavGraph(navController: NavHostController, sharedViewModel: SharedViewModel) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = BottomBarScreen.WatchList.route) {
            BookmarksScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        detailsNavGraph(navController = navController, sharedViewModel = sharedViewModel)
    }
}

@OptIn(ExperimentalPagingApi::class)
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController, sharedViewModel: SharedViewModel) {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.Details.route
    ) {
        composable(route = Screen.Details.route) {
            DetailsScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }
}