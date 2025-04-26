package com.example.foodhub.screens.login

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodhub.data.local.UserSessionManager
import com.example.foodhub.navigation.Screen
import com.example.foodhub.navigation.Screen.BottomBarScreen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, userSessionManager: UserSessionManager) {
    // Input fields state
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    // Button scale animation
    val buttonScale = remember { Animatable(1f) }

    // Handle UI animations
    LaunchedEffect(email, password) {
        buttonScale.animateTo(
            targetValue = 1.1f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
        buttonScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    // Handle login logic
    val context = LocalContext.current
    val validEmail = "test@foodhub.com"
    val validPassword = "123456"

    // Remember CoroutineScope to launch coroutine
    val coroutineScope = rememberCoroutineScope()

    // Function to handle login logic
    val handleLogin = {
        val userId = "user123"
        val userName = "testName"
        if (email.value == validEmail && password.value == validPassword) {
            // Launch coroutine to call suspend function
            coroutineScope.launch {
                userSessionManager.saveLoginStatus(true, email.value, userId, userName)
            }
            navController.navigate(BottomBarScreen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        } else {
            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

    // Composable for UI (calling the LoginForm)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFF3E5F5)) // White to light purple
                )
            )
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            LoginForm(
                email = email.value,
                onEmailChange = { email.value = it },
                password = password.value,
                onPasswordChange = { password.value = it },
                onLoginClick = handleLogin,
                buttonScale = buttonScale.value
            )
        }
    }
}


//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(navController = NavController(LocalContext.current))
//}