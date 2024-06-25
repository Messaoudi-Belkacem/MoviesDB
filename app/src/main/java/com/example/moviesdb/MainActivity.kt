package com.example.moviesdb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.data.repository.Repository
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.navigation.RootNavigationGraph
import com.example.moviesdb.ui.theme.MoviesDBTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    private val tag = "MainActivity.kt"
    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate method is called")

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                sharedViewModel.condition.value
            }
        }

        enableEdgeToEdge()
        // Handle the intent when the activity is created
        handleIntent(intent)

        setContent {
            MoviesDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navHostController = rememberNavController()
                    RootNavigationGraph(
                        navHostController = navHostController,
                        sharedViewModel = sharedViewModel,
                        startDestination = sharedViewModel.startDestination.value
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Log.d(tag, "onNewIntent method is called")
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        Log.d(tag, "handleIntent method is called")
        val data: Uri? = intent.data
        Log.d(tag, data.toString())
        data?.let {
            val requestToken = it.getQueryParameter("request_token")
            requestToken?.let { requestToken ->
                // Now you have the request token and can create a session ID
                sharedViewModel.setRequestToken(requestToken = requestToken)
            }
            val approved = it.getQueryParameter("approved")
            approved?.let { approved ->
                // Now you have the request token and can create a session ID
                if (approved == "true") {
                    sharedViewModel.setApproved(approved = true)
                } else {
                    sharedViewModel.setApproved(approved = false)
                }
            }
        }
    }
}