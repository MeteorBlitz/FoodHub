package com.example.foodhub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodhub.navigation.Screen.BottomBarScreen
import com.example.foodhub.screens.HomeScreen
import com.example.foodhub.screens.LoginScreen
import com.example.foodhub.screens.ProfileScreen
import com.example.foodhub.screens.RestaurantDetailScreen
import com.example.foodhub.screens.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        // Non-bottom bar screens
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }

        // Bottom bar screens
        composable(BottomBarScreen.Home.route) { HomeScreen(navController) }
        composable(BottomBarScreen.Profile.route) { ProfileScreen(navController) }

        // Detail screen with dynamic argument
        composable("${Screen.RestaurantDetail.route}/{restaurantId}") { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
            restaurantId?.let {
                RestaurantDetailScreen(navController, it)
            }
        }
    }
}