package com.example.foodhub.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodhub.components.FoodHubLogo
import com.example.foodhub.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    // Input fields state
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    // Button animation state
    val buttonScale = remember { Animatable(1f) }

    // Logo animation state
    val logoAlpha = remember { Animatable(0f) }

    // Handle focus changes (e.g., email field focus)
    val emailFocusRequester = FocusRequester.Default
    val passwordFocusRequester = FocusRequester.Default

    val context = LocalContext.current

    // Trigger button animation on value change
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

    // Trigger logo fade-in animation
    LaunchedEffect(Unit) {
        logoAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    // Login Screen UI
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
            // Animated Logo with fade-in
            Image(
                painter = rememberVectorPainter(FoodHubLogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
                    .alpha(logoAlpha.value) // Applying fade-in effect
            )
            Text(
                text = "FoodHub",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            )

            // Email field with focus animation
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = {  Text("Email", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester)
                    .animateContentSize()
                    .padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6200EE)),
            )

            // Password field with focus animation
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password",color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester)
                    .animateContentSize()
                    .padding(bottom = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6200EE)
                ) ,
            )

            // Login Button with scaling animation
            Button(
                onClick = {
                    val validEmail = "test@foodhub.com"
                    val validPassword = "123456"

                    if (email.value == validEmail && password.value == validPassword) {
                        saveLoginStatus(context, true) // Save to SharedPreferences
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(buttonScale.value)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Login", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(navController = NavController(LocalContext.current))
//}