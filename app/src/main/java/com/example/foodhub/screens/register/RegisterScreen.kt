package com.example.foodhub.screens.register

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodhub.navigation.Screen
import com.example.foodhub.screens.login.CurvedTopBackground
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun RegisterScreen(
    navController: NavController,
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val nameError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }

    val isLoading = remember { mutableStateOf(false) }
    val buttonScale = remember { Animatable(1f) }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Animate button
    LaunchedEffect(name.value, email.value, password.value) {
        buttonScale.animateTo(1.1f, tween(300, easing = FastOutSlowInEasing))
        buttonScale.animateTo(1f, tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CurvedTopBackground(title = "Create Account")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 240.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterForm(
                name = name.value,
                onNameChange = { if (it.length <= 30) name.value = it },
                nameError = nameError.value,
                email = email.value,
                onEmailChange = { if (it.length <= 50) email.value = it },
                emailError = emailError.value,
                password = password.value,
                onPasswordChange = { if (it.length <= 12) password.value = it },
                passwordError = passwordError.value,
                onRegisterClick = {
                    nameError.value = ""
                    emailError.value = ""
                    passwordError.value = ""

                    if (name.value.isBlank()) {
                        nameError.value = "Name is required"
                        return@RegisterForm
                    }

                    if (email.value.isBlank()) {
                        emailError.value = "Email is required"
                        return@RegisterForm
                    } else if (!isValidEmail(email.value)) {
                        emailError.value = "Invalid email format"
                        return@RegisterForm
                    }

                    if (password.value.isBlank()) {
                        passwordError.value = "Password is required"
                        return@RegisterForm
                    } else if (password.value.length < 6) {
                        passwordError.value = "Minimum 6 characters"
                        return@RegisterForm
                    }

                    isLoading.value = true
                    auth.createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name.value.trim())
                                    .build()

                                user?.updateProfile(profileUpdates)
                                    ?.addOnCompleteListener { profileTask ->
                                        if (profileTask.isSuccessful) {
                                            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                            isLoading.value = false
                                            navController.navigate(Screen.Login.route) {
                                                popUpTo(Screen.Login.route) { inclusive = true }
                                            }
                                        } else {
                                            isLoading.value = false
                                            Toast.makeText(context, "Name not saved", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                isLoading.value = false
                                val errorText = task.exception?.message ?: "Registration failed"
                                Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
                            }
                        }
                },
                buttonScale = buttonScale.value,
                isLoading = isLoading.value,
                onLoginInsteadClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
