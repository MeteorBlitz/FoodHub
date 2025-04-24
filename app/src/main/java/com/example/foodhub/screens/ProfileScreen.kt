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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodhub.navigation.Screen
import com.example.foodhub.navigation.Screen.BottomBarScreen

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text("Name: FoodHub User", style = MaterialTheme.typography.bodyLarge)
            Text("Email: user@example.com", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    saveLoginStatus(context, false)  // clear login status
                    navController.navigate(Screen.Login.route) {
                        popUpTo(BottomBarScreen.Home.route) { inclusive = true } // clear backstack
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Logout", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}