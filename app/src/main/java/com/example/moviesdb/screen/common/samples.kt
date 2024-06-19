package com.example.moviesdb.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviesdb.R
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.navigation.BottomBarScreen
import com.example.moviesdb.ui.theme.MyBlue80
import com.example.moviesdb.util.Constants.Companion.IMAGES_BASIC_URL

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    val bottomBarScreens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.WatchList,
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = bottomBarScreens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        Column {
            Divider(
                color = MyBlue80,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                bottomBarScreens.forEach { screen ->
                    NavItem(
                        bottomBarScreen = screen,
                        currentDestination = currentDestination,
                        navController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun NavItem(
    bottomBarScreen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == bottomBarScreen.route } == true

    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Transparent)
            .clickable(onClick = {
                navController.navigate(bottomBarScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        val iconID = if (selected) bottomBarScreen.painterSelected else bottomBarScreen.painterUnselected
        Icon(
            painter = painterResource(id = iconID),
            tint = Color.Unspecified,
            contentDescription = "icon",
            modifier = Modifier.size(25.dp)
        )
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(140.dp)
            .height(210.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(IMAGES_BASIC_URL + movie.posterPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.error_icon),
            modifier = Modifier.fillMaxSize()
        )
    }
}