package com.example.foodhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodhub.screens.HomeScreen
import com.example.foodhub.screens.LoginScreen
import com.example.foodhub.screens.RestaurantDetailScreen
import com.example.foodhub.screens.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.RestaurantDetail.route + "/{restaurantId}") { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
            restaurantId?.let {
                RestaurantDetailScreen(navController, it)
            }
        }

    }
}