package com.example.foodhub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodhub.data.local.UserSessionManager
import com.example.foodhub.navigation.Screen.BottomBarScreen
import com.example.foodhub.screens.HomeScreen
import com.example.foodhub.screens.login.LoginScreen
import com.example.foodhub.screens.profile.ProfileScreen
import com.example.foodhub.screens.SplashScreen
import com.example.foodhub.screens.details.RestaurantDetailScreen
import com.example.foodhub.ui.viewmodel.ProfileViewModel
import com.example.foodhub.ui.viewmodel.ProfileViewModelFactory

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Get the context using LocalContext.current
    val context = LocalContext.current
    // Create the UserSessionManager with the context
    val userSessionManager = UserSessionManager(context)
    // Create ProfileViewModel using factory
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(userSessionManager)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        // Non-bottom bar screens
        composable(Screen.Splash.route) { SplashScreen(navController,userSessionManager) }
        composable(Screen.Login.route) { LoginScreen(navController,userSessionManager) }

        // Bottom bar screens
        composable(BottomBarScreen.Home.route) { HomeScreen(navController) }
        composable(BottomBarScreen.Profile.route) { ProfileScreen(navController,profileViewModel) }

        // Detail screen with dynamic argument
        composable("${Screen.RestaurantDetail.route}/{restaurantId}") { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
            restaurantId?.let {
                RestaurantDetailScreen(navController, it)
            }
        }
    }
}