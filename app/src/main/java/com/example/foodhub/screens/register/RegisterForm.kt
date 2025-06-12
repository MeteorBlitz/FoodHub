package com.example.foodhub.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.foodhub.components.FoodHubLogo

@Composable
fun RegisterForm(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String,
    onRegisterClick: () -> Unit,
    buttonScale: Float,
    isLoading: Boolean,
    onLoginInsteadClick: () -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        // App Logo
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
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                if (it.length <= 20) onNameChange(it)
            },
            label = { Text("Name", color = Color.Gray) },
            isError = nameError.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6200EE))
        )
        if (nameError.isNotEmpty()) {
            Text(nameError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                if (it.length <= 40) onEmailChange(it)
            },
            label = { Text("Email", color = Color.Gray) },
            isError = emailError.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6200EE))
        )
        if (emailError.isNotEmpty()) {
            Text(emailError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                if (it.length <= 8) onPasswordChange(it)
            },
            label = { Text("Password") },
            isError = passwordError.isNotEmpty(),
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    imageVector = if (showPassword.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (showPassword.value) "Hide password" else "Show password",
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        showPassword.value = !showPassword.value
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        if (passwordError.isNotEmpty()) {
            Text(passwordError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                color = Color(0xFF6200EE),
                strokeWidth = 3.dp
            )
        } else {
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(buttonScale)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Register", style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Already have an account?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Login",
                color = Color(0xFF6200EE),
                modifier = Modifier.clickable(onClick = onLoginInsteadClick)
            )
        }
    }
}
