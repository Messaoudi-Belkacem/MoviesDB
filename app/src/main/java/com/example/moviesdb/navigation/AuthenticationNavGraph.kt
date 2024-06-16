package com.example.moviesdb.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moviesdb.screen.LoginScreen
import com.example.moviesdb.screen.ScreenContent

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthenticationScreen.Login.route
    ) {
        composable(route = AuthenticationScreen.Login.route) {
            LoginScreen(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthenticationScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthenticationScreen.Forgot.route)
                }
            )
        }
        composable(route = AuthenticationScreen.SignUp.route) {
            ScreenContent(name = AuthenticationScreen.SignUp.route) {}
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