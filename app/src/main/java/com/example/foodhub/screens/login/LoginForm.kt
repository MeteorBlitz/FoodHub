package com.example.foodhub.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.foodhub.components.FoodHubLogo

@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    buttonScale: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        // Logo and title
        Image(
            painter = rememberVectorPainter(FoodHubLogo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "FoodHub",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF6200EE),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp)
        )

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6200EE)),
        )

        // Password input field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password", color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6200EE)),
        )

        // Login button with scaling animation
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .scale(buttonScale)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "Login", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GoogleSignInButton(onClick = onGoogleSignInClick)
        }
    }
}
