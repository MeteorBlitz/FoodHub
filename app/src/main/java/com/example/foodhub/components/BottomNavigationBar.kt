package com.example.foodhub.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.example.foodhub.navigation.bottomBarScreens
import com.example.foodhub.ui.viewmodel.ProfileViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    viewModel: ProfileViewModel = viewModel()  // Access ViewModel to check isLoggedIn state
) {
    // Observe the login state from ViewModel
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value

    NavigationBar {
        bottomBarScreens.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = selected,
                onClick = {
                    // Check if the Profile screen is selected and user is logged in
                    if (!selected) {
                        if (screen.route == "profile" && !isLoggedIn) {
                            // If Profile tab is clicked and user is logged out, navigate to Login screen
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } else {
                            // If not the Profile tab or user is logged in, navigate to the selected screen
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            )
        }
    }
}


