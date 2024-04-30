package com.example.moviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviesdb.navigation.Screen
import com.example.moviesdb.navigation.SetupNavGraph
import com.example.moviesdb.screen.MainScreen
import com.example.moviesdb.ui.theme.MoviesDBTheme

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private val startDestination = Screen.Main.route
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MoviesDBTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navHostController = rememberNavController()
                    MainScreen(navHostController = navHostController)
                }
            }
        }
    }
}