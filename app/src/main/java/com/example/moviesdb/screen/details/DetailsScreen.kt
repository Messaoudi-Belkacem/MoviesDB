package com.example.moviesdb.screen.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.TypeSpecimen
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviesdb.R
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.data.model.CastMember
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.data.model.Review
import com.example.moviesdb.data.state.AddToWatchlistState
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.CastMemberComposable
import com.example.moviesdb.screen.common.ReviewComposable
import com.example.moviesdb.screen.home.LoadingCircle
import com.example.moviesdb.util.Constants.Companion.IMAGES_BASIC_URL

@OptIn(ExperimentalPagingApi::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val tag = "DetailsScreen.kt"

    val tabs = listOf("About the movie", "Reviews", "Cast")

    var tabIndex by remember { mutableIntStateOf(0) }

    val movie: Movie? = sharedViewModel.selectedMovie.value

    val reviews = detailsViewModel.reviewsFlow.collectAsLazyPagingItems()
    val cast = detailsViewModel.castFlow.collectAsLazyPagingItems()
    val addToWatchlistState = detailsViewModel.addToWatchlist

    LaunchedEffect(Unit) {
        if (movie != null) {
            detailsViewModel.getReviews(movieID = movie.id)
            detailsViewModel.getCast(movieID = movie.id)
        } else {
            Log.d(tag, "movie is null")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(
                    "Detail",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                IconButton(
                    onClick = {
                        if (movie != null) {
                            detailsViewModel.addToWatchlist(
                                accountID = sharedViewModel.user.value?.id ?: 20139247,
                                sessionID = sharedViewModel.sessionID.value,
                                mediaID = movie.id
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Bookmark,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                /*when(addToWatchlistState) {
                    is (AddToWatchlistState.Initial) -> {

                    }
                    is (AddToWatchlistState.Loading) -> {

                    }
                    is (AddToWatchlistState.Success) -> {

                    }
                    is (AddToWatchlistState.Error) -> {

                    }
                }*/
                if (movie == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {  },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Go Back",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        }
                    }
                    Toast.makeText(
                        LocalContext.current,
                        "Unexpected error",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    ConstraintLayout {
                        val (image, titleAndPoster) = createRefs()
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(IMAGES_BASIC_URL + movie.backdropPath)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            error = painterResource(id = R.drawable.error_icon),
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(3f / 2f)
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                }
                        )
                        TitleAndPoster(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .constrainAs(titleAndPoster) {
                                    top.linkTo(image.bottom, margin = (-90).dp)
                                },
                            movie = movie
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        InfoRow(imageVector = Icons.Rounded.CalendarToday, text = movie.releaseDate)
                        Spacer(modifier = Modifier.width(12.dp))
                        VerticalDivider(thickness = 1.dp)
                        Spacer(modifier = Modifier.width(12.dp))
                        InfoRow(imageVector = Icons.Rounded.AccessTime, text = movie.voteCount.toString())
                        Spacer(modifier = Modifier.width(12.dp))
                        VerticalDivider(thickness = 1.dp)
                        Spacer(modifier = Modifier.width(12.dp))
                        InfoRow(imageVector = Icons.Rounded.TypeSpecimen, text = "Action")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        TabRow(
                            selectedTabIndex = tabIndex
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(text = title, fontSize = 12.sp) },
                                    selected = tabIndex == index,
                                    onClick = { tabIndex = index }
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when (tabIndex) {
                                0 -> {
                                    Text(
                                        text = movie.overview,
                                        modifier = Modifier.padding(8.dp),
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                1 -> {
                                    when (reviews.loadState.refresh) {
                                        is LoadState.Loading -> {
                                            LoadingCircle()
                                        }
                                        is LoadState.Error -> {
                                            Button(
                                                onClick = { /* TODO */ },
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = "RETRY",
                                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                                )
                                            }
                                            Toast.makeText(
                                                LocalContext.current,
                                                "Unexpected error",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            (reviews.loadState.refresh as LoadState.Error).error.message?.let {
                                                Log.d(tag,
                                                    it
                                                )
                                            }
                                            (reviews.loadState.refresh as LoadState.Error).error.printStackTrace()
                                        }
                                        else -> {
                                            ReviewTabContent(
                                                reviewsLazyPagingItems = reviews
                                            )
                                        }
                                    }
                                }
                                2 -> {
                                    when (cast.loadState.refresh) {
                                        is LoadState.Loading -> {
                                            LoadingCircle()
                                        }
                                        is LoadState.Error -> {
                                            Button(
                                                onClick = { /* TODO */ },
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = "RETRY",
                                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                                )
                                            }
                                            Toast.makeText(
                                                LocalContext.current,
                                                "Unexpected error",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            (reviews.loadState.refresh as LoadState.Error).error.message?.let {
                                                Log.d(tag,
                                                    it
                                                )
                                            }
                                            (reviews.loadState.refresh as LoadState.Error).error.printStackTrace()
                                        }
                                        else -> {
                                            CastTabContent(
                                                castLazyPagingItems = cast
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TitleAndPoster(
    modifier: Modifier,
    movie: Movie
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(IMAGES_BASIC_URL + movie.posterPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.error_icon),
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
        Text(
            text = movie.title,
            modifier = Modifier
                .padding(8.dp),
            color = MaterialTheme.typography.titleLarge.color,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
    }
}

@Composable
fun InfoRow(
    imageVector: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            modifier = Modifier,
            color = MaterialTheme.typography.titleLarge.color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ReviewTabContent(
    reviewsLazyPagingItems: LazyPagingItems<Review>?,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(8.dp)

    ) {
        items(count = reviewsLazyPagingItems!!.itemCount) { index ->
            val item = reviewsLazyPagingItems[index]
            if (item != null) {
                ReviewComposable(review = item)
                HorizontalDivider(thickness = 1.dp)
            } else {
                Log.d("DetailsScreen.kt", "item number $index is null")
            }
        }

    }
}

@Composable
fun CastTabContent(
    castLazyPagingItems: LazyPagingItems<CastMember>?,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(count = castLazyPagingItems!!.itemCount) { index ->
            val item = castLazyPagingItems[index]
            if (item != null) {
                CastMemberComposable(castMember = item)
            } else {
                Log.d("DetailsScreen.kt", "item number $index is null")
            }
        }
    }
}