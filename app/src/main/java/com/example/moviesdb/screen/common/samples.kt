package com.example.moviesdb.screen.common

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HowToVote
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.ExperimentalPagingApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.moviesdb.R
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.data.model.CastMember
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.Review
import com.example.moviesdb.navigation.BottomBarScreen
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.ui.theme.MyBlue80
import com.example.moviesdb.util.Constants.Companion.AVATARS_BASIC_URL
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
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = MyBlue80
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

@OptIn(ExperimentalPagingApi::class)
@Composable
fun MovieItem(
    movie: Movie,
    modifier: Boolean?,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    Card(
        modifier = if (modifier == null) {
            Modifier
                .height(210.dp)
                .width(140.dp)
                .clickable {
                    navController.popBackStack()
                    navController.navigate(Graph.DETAILS)
                    sharedViewModel.setSelectedMovie(movie)
                }
        } else {
            Modifier
                .height(240.dp)
                .width(160.dp)
                .clickable {
                    navController.popBackStack()
                    navController.navigate(Graph.DETAILS)
                    sharedViewModel.setSelectedMovie(movie)
                }
        },
        shape = RoundedCornerShape(16.dp)
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

@Composable
fun SimpleOutlinedTextFieldSample(
    label: String,
    modifier: Modifier,
    onTextChanged: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        placeholder = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        textStyle = TextStyle.Default.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium)
    )
}

@Composable
fun ReviewComposable(
    review: Review
) {
    Log.d("ReviewComposable", review.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(AVATARS_BASIC_URL + review.authorDetails.avatarPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.account_icon),
            modifier = Modifier
                .padding(8.dp)
                .size(62.dp)
                .clip(CircleShape),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = review.author,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = review.content,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
    }
}

@Composable
fun CastMemberComposable(
    castMember: CastMember
) {
    Log.d("ReviewComposable", castMember.toString())
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(AVATARS_BASIC_URL + castMember.profilePath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.profile_icon),
            modifier = Modifier
                .padding(8.dp)
                .size(128.dp)
                .clip(CircleShape),
        )
        Text(
            text = castMember.name,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun SearchListContent(
    movie: Movie,
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    Log.d("SearchListContent", movie.toString())
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.popBackStack()
                navController.navigate(Graph.DETAILS)
                sharedViewModel.setSelectedMovie(movie)
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(IMAGES_BASIC_URL + movie.posterPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.image_icon),
            modifier = Modifier
                .height(180.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(16.dp)),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.StarOutline,
                    contentDescription = null,
                    tint = Color(0xFFFF8700),
                    modifier = Modifier
                        .size(16.dp)
                )
                Text(
                    text = movie.voteAverage.toString(),
                    fontSize = 12.sp,
                    color = Color(0xFFFF8700)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.HowToVote,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                )
                Text(
                    text = movie.voteCount.toString(),
                    fontSize = 12.sp,
                )
            }
            /*Text(
                text = movie.genreIds.toString(),
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )*/
        }
    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.movies_lottie_animation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}