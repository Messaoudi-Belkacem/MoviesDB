package com.example.moviesdb.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.moviesdb.R

sealed class BottomBarScreen(val route: String, val painterSelected: Int, val painterUnselected: Int) {
    data object Home: BottomBarScreen("home_screen", R.drawable.home_selected_icon, R.drawable.home_unselected_icon)
    data object Search: BottomBarScreen(route = "search_screen", R.drawable.search_selected_icon, R.drawable.search_unselected_icon)
    data object WatchList: BottomBarScreen(route = "bookmarks_screen", R.drawable.bookmark_selected_icon, R.drawable.bookmark_unselected_icon)
}