package com.example.moviesdb.screen.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviesdb.navigation.AuthenticationScreen
import com.example.moviesdb.screen.common.SimpleOutlinedTextFieldSample

@Composable
fun SignUpScreen(
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
                text = "Create Account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Create an account so you can explore all the\n" +
                        "movies",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
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
                verticalArrangement = Arrangement.spacedBy(26.dp)
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
                        email = enteredText
                    })
                SimpleOutlinedTextFieldSample(
                    label = "Confirm Password",
                    modifier = Modifier
                        .width(357.dp),
                    onTextChanged = { enteredText ->
                        password = enteredText
                    })
            }
            Text(
                text = "Forgot your password?",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            )
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 144.dp, vertical = 15.dp)
            ) {
                Text(
                    text = "Sign up",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "Already have an account",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate(AuthenticationScreen.Login.route)
                    }
            )
        }
    }
}