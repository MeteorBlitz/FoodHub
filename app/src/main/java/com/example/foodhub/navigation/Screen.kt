package com.example.foodhub.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object RestaurantDetail : Screen("restaurantDetail")

    // Bottom bar screens with icon and title
    sealed class BottomBarScreen(
        route: String,
        val title: String,
        val icon: ImageVector
    ) : Screen(route) {
        object Home : BottomBarScreen("home", "Home", Icons.Default.Home)
        object Profile : BottomBarScreen("profile", "Profile", Icons.Default.Person)
    }
}
val bottomBarScreens = listOf(
    Screen.BottomBarScreen.Home,
    Screen.BottomBarScreen.Profile
)