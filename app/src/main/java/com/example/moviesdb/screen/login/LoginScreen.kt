package com.example.moviesdb.screen.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.moviesdb.navigation.AuthenticationScreen
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.AnimatedPreloader
import com.example.moviesdb.screen.common.SimpleOutlinedTextFieldSample

@OptIn(ExperimentalPagingApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val tag = "LoginScreen.kt"
    /*var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }*/
    val loginState by loginViewModel.loginState
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (sharedViewModel.requestToken.value.isNotEmpty()) {
            loginViewModel.setLoginState(LoginState.AuthenticationSuccess)
        } else Log.d(tag, "create session request token is empty")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            Text(
                text = "Login Here",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Welcome back we’ve,\n" +
                        "missed you!",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            /*Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                SimpleOutlinedTextFieldSample(
                    label = "Email",
                    modifier = Modifier
                        .width(357.dp),
                    onTextChanged = { enteredText ->
                        email = enteredText
                    })
                SimpleOutlinedTextFieldSample(
                    label = "Password",
                    modifier = Modifier
                        .width(357.dp),
                    onTextChanged = { enteredText ->
                        password = enteredText
                    })
            }*/
            Text(
                modifier = Modifier
                    .clickable {
                        /*navController.navigate(AuthenticationScreen.Forgot.route)*/
                    }
                    .align(Alignment.CenterHorizontally),
                text = "Forgot your password?",
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
            )
            when (loginState) {
                is LoginState.Initial -> {
                    LoginScreenMainButton(
                        text = "Create Request Token",
                        onClick = {
                            loginViewModel.getRequestToken()
                        }
                    )
                }
                is LoginState.CreateRequestTokenSuccess -> {
                    LoginScreenMainButton(
                        text = "Authenticate",
                        onClick = {
                            loginViewModel.askForUserPermission(context = context)
                        }
                    )
                }

                is LoginState.AuthenticationSuccess -> {
                    LoginScreenMainButton(
                        text = "Create a session",
                        onClick = {
                            loginViewModel.createSessionId(
                                requestToken = sharedViewModel.requestToken.value,
                                approved = sharedViewModel.approved.value
                            )
                        }
                    )
                }
                is LoginState.CreateSessionIDSuccess -> {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                }
                is LoginState.Error -> TODO()
                is LoginState.Loading -> {
                    AnimatedPreloader(modifier = Modifier.size(300.dp))
                }
            }
            Text(
                text = "Create new account",
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                modifier = Modifier
                    .clickable {
                        /*navController.navigate(AuthenticationScreen.SignUp.route)*/
                    }
            )
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
        modifier = Modifier
            .fillMaxWidth(0.85f)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}