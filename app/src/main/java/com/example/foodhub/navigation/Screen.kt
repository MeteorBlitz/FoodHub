package com.example.foodhub.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object RestaurantDetail : Screen("restaurantDetail")
}