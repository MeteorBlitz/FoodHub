package com.example.foodhub.screens.register

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.foodhub.navigation.Screen
import com.example.foodhub.screens.login.CurvedTopBackground

@Composable
fun RegisterScreen(
    navController: NavController,
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val buttonScale = remember { Animatable(1f) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Animate button
    LaunchedEffect(name.value, email.value, password.value) {
        buttonScale.animateTo(1.1f, tween(300, easing = FastOutSlowInEasing))
        buttonScale.animateTo(1f, tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CurvedTopBackground(title = "Create Account")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 240.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterForm(
                name = name.value,
                onNameChange = { name.value = it },
                email = email.value,
                onEmailChange = { email.value = it },
                password = password.value,
                onPasswordChange = { password.value = it },
                onRegisterClick = {
                    // TODO: Replace with real Firebase logic later
                    Toast.makeText(context, "Register clicked!", Toast.LENGTH_SHORT).show()
                },
                buttonScale = buttonScale.value,
                onLoginInsteadClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
    }
}