package com.example.foodhub.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodhub.components.FoodHubLogo
import com.example.foodhub.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animation state
    val alpha = remember { Animatable(0f) }
    val context = LocalContext.current

    // Start animation
    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        )
        // Wait for 2 seconds before navigating
        delay(2000)
        if (getLoginStatus(context)) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // Splash Screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Your theme color here
            .wrapContentSize(align = Alignment.Center)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                //painter = painterResource(id = R.drawable.ic_logo),
                painter = rememberVectorPainter(FoodHubLogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(140.dp)
                    .scale(alpha.value)
            )

            // App Name
            Text(
                text = "FoodHub",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF6200EE),
                modifier = Modifier
                    .alpha(alpha.value)
                    .padding(top = 32.dp)
            )
        }
    }
}

//@Preview
//@Composable
//fun SplashScreenPreview() {
//    FoodHubTheme {
//        SplashScreen(navController = NavController(LocalContext.current))
//    }
//}
