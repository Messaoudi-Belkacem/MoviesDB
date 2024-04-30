package com.example.moviesdb.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    data object Main: Screen(route = "main_screen", Icons.Rounded.Home)
    data object Home: Screen(route = "home_screen", Icons.Rounded.Home)
    data object Search: Screen(route = "search_screen", Icons.Rounded.Search)
    data object WatchList: Screen(route = "watch_list_screen", Icons.Rounded.Bookmark)
}