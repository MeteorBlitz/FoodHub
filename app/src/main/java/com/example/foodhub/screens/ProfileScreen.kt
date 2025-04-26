package com.example.foodhub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodhub.data.local.UserSessionManager
import com.example.foodhub.navigation.Screen
import com.example.foodhub.navigation.Screen.BottomBarScreen
import com.example.foodhub.ui.viewmodel.ProfileViewModel
import com.example.foodhub.ui.viewmodel.ProfileViewModelFactory

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value

    // Trigger navigation when isLoggedIn is false
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screen.Login.route) {
                // Ensuring we pop back to Home or any previous route when navigating to login
                popUpTo(BottomBarScreen.Home.route) { inclusive = true }
            }
        }
    }

    // UI for Profile Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Profile", style = MaterialTheme.typography.titleMedium)
        Text("Name: ${viewModel.userName.collectAsState().value}")
        Text("Email: ${viewModel.userEmail.collectAsState().value}")

        Button(
            onClick = { viewModel.logout() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }
}