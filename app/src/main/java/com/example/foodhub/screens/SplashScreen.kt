package com.example.foodhub.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodhub.R
import com.example.foodhub.navigation.Screen
import com.example.foodhub.ui.theme.FoodHubTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animation state
    val scale = remember { Animatable(0f) }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6200EE), Color(0xFF3700B3))
    )

    // Start animation
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )
        // Wait for 2 seconds before navigating
        delay(2000)
        navController.navigate(Screen.Login.route)
    }

    // Splash Screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient) // Your theme color here
            .wrapContentSize(align = Alignment.Center)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo), // Replace with your logo
            contentDescription = "Logo",
            modifier = Modifier
                .scale(scale.value)
                .size(200.dp) // Adjust logo size
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    FoodHubTheme {
        SplashScreen(navController = NavController(LocalContext.current))
    }
}
