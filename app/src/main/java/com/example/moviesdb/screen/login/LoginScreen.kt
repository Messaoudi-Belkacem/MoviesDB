package com.example.moviesdb.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moviesdb.navigation.AuthenticationScreen
import com.example.moviesdb.navigation.Graph
import com.example.moviesdb.screen.common.SimpleOutlinedTextFieldSample

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            Column(
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
            }
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate(AuthenticationScreen.Forgot.route)
                    }
                    .align(Alignment.CenterHorizontally),
                text = "Forgot your password?",
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
            )
            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                          },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 144.dp, vertical = 15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Sign in",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = "Create new account",
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                modifier = Modifier
                    .clickable {
                        navController.navigate(AuthenticationScreen.SignUp.route)
                    }
            )
        }
    }
}

