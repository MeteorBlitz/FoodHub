package com.example.foodhub.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodhub.navigation.Screen
import com.example.foodhub.navigation.Screen.BottomBarScreen
import com.example.foodhub.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
    val name = viewModel.userName.collectAsState().value
    val email = viewModel.userEmail.collectAsState().value

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screen.Login.route) {
                popUpTo(BottomBarScreen.Home.route) { inclusive = true }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Header Section - 25% height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Circle Avatar with Initial
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(name, color = MaterialTheme.colorScheme.onPrimary)
                Text(email, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
            }
        }

        // Bottom List Section - 75% height
        Column(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileOption(icon = Icons.Default.ShoppingCart, label = "My Orders") {}
            ProfileOption(icon = Icons.Default.Settings, label = "Settings") {}
            ProfileOption(icon = Icons.Default.DarkMode, label = "Toggle Theme") {}

            Spacer(modifier = Modifier.height(12.dp))

            ProfileOption(
                icon = Icons.AutoMirrored.Filled.Logout,
                label = "Logout",
                iconTint = MaterialTheme.colorScheme.error,
                textColor = MaterialTheme.colorScheme.error
            ) {
                viewModel.logout()
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}