package com.example.moviesdb.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import com.example.moviesdb.SharedViewModel
import com.example.moviesdb.data.state.LoginState
import com.example.moviesdb.data.state.LoginSubState
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.AnimatedPreloader
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val tag = "LoginScreen.kt"
    val loginState by loginViewModel.loginState
    val loginSubState by loginViewModel.loginSubState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (sharedViewModel.requestToken.value.isNotEmpty()) {
            loginViewModel.setLoginState(LoginState.AuthenticationSuccess)
        } else {
            Log.d(tag, "create session request token is empty because the authentication failed or app had a clean restart")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HeaderSection()
        ContentSection(loginState, loginSubState, navController, loginViewModel, sharedViewModel, context)
    }
}

@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        Text(
            text = "Login Here",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Welcome back we’ve,\nmissed you!",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun ContentSection(
    loginState: LoginState,
    loginSubState: LoginSubState,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    sharedViewModel: SharedViewModel,
    context: Context
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        ForgotPasswordText()
        LoginStateContent(loginState, navController, loginViewModel, loginSubState, sharedViewModel, context)
        CreateAccountText(navController)
    }
}

@Composable
fun ForgotPasswordText() {
    Text(
        modifier = Modifier.clickable { /*navController.navigate(AuthenticationScreen.Forgot.route)*/ },
        text = "Forgot your password?",
        fontWeight = FontWeight.SemiBold,
        fontSize = MaterialTheme.typography.labelMedium.fontSize,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CreateAccountText(navController: NavHostController) {
    Text(
        modifier = Modifier.clickable { /*navController.navigate(AuthenticationScreen.SignUp.route)*/ },
        text = "Create new account",
        fontWeight = FontWeight.SemiBold,
        fontSize = MaterialTheme.typography.labelLarge.fontSize,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun LoginStateContent(
    loginState: LoginState,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    loginSubState: LoginSubState,
    sharedViewModel: SharedViewModel,
    context: Context
) {

    val coroutineScope = rememberCoroutineScope()

    when (loginState) {
        is LoginState.Initial -> {
            LoginScreenMainButton(
                text = "Create Request Token",
                onClick = { loginViewModel.getRequestToken() }
            )
        }
        is LoginState.CreateRequestTokenSuccess -> {
            LoginScreenMainButton(
                text = "Authenticate",
                onClick = { loginViewModel.askForUserPermission(context) }
            )
        }
        is LoginState.AuthenticationSuccess -> {
            LoginScreenMainButton(
                text = "Create a session",
                onClick = {
                    coroutineScope.launch {
                        loginViewModel.createSessionId(
                            requestToken = sharedViewModel.requestToken.value,
                            approved = sharedViewModel.approved.value
                        )
                    }
                }
            )
        }
        is LoginState.Error -> {
            /*TODO*/
        }
        is LoginState.Loading -> {
            AnimatedPreloader(modifier = Modifier.size(64.dp))
            when(loginSubState) {
                is LoginSubState.Initial -> {

                }
                is LoginSubState.CreateRequestTokenSuccess -> {
                    loginViewModel.saveSessionIDToDatastore(loginViewModel.getSessionID())
                    sharedViewModel.setSessionID(loginViewModel.getSessionID())
                    sharedViewModel.getUser()
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                }
                is LoginSubState.Error -> {
                    /*TODO*/
                }
            }
        }
    }
}

@Composable
fun LoginScreenMainButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.fillMaxWidth(0.85f)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}