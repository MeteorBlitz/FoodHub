package com.example.foodhub.screens.login

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val buttonScale = remember { Animatable(1f) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val validEmail = "test@foodhub.com"
    val validPassword = "123456"

    val handleLogin = {
        val userId = "user123"
        val userName = "testName"
        if (email.value == validEmail && password.value == validPassword) {
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

    // Animate button scale on launch
    LaunchedEffect(email, password) {
        buttonScale.animateTo(1.1f, animationSpec = tween(300, easing = FastOutSlowInEasing))
        buttonScale.animateTo(1f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        // Curved background with welcome text
        CurvedTopBackground()

        // Login form positioned below the curve
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 250.dp), // Adjusted to reduce white space
            horizontalAlignment = Alignment.CenterHorizontally
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