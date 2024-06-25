@file:OptIn(ExperimentalPagingApi::class)

package com.example.moviesdb.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.screen.login.LoginScreen
import com.example.moviesdb.screen.ScreenContent
import com.example.moviesdb.screen.register.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController, sharedViewModel: SharedViewModel) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthenticationScreen.Login.route
    ) {
        composable(route = AuthenticationScreen.Login.route) {
            LoginScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        composable(route = AuthenticationScreen.SignUp.route) {
            SignUpScreen(
                navController = navController
            )
        }
        composable(route = AuthenticationScreen.Forgot.route) {
            ScreenContent(name = AuthenticationScreen.Forgot.route) {}
        }
    }
}

sealed class AuthenticationScreen(val route: String) {
    object Login : AuthenticationScreen(route = "LOGIN")
    object SignUp : AuthenticationScreen(route = "SIGN_UP")
    object Forgot : AuthenticationScreen(route = "FORGOT")
}