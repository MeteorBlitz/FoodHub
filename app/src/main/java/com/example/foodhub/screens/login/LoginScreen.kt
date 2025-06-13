package com.example.foodhub.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodhub.data.local.UserSessionManager
import com.example.foodhub.navigation.Screen
import com.example.foodhub.navigation.Screen.BottomBarScreen
import com.example.foodhub.ui.viewmodel.AuthState
import com.example.foodhub.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    navController: NavController,
    userSessionManager: UserSessionManager,
    authViewModel: AuthViewModel = viewModel()
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val buttonScale = remember { Animatable(1f) }
    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsState()
    val googleSignInLoading = remember { mutableStateOf(false) }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        googleSignInLoading.value = false
        if (result.resultCode == Activity.RESULT_OK) {
            authViewModel.handleGoogleSignInResult(result.data)
        } else {
            Toast.makeText(context, "Google Sign-In cancelled or failed.", Toast.LENGTH_SHORT).show()
            authViewModel.resetAuthState()
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.initGoogleSignIn(context)
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                val data = authState as AuthState.Success
                userSessionManager.saveLoginStatus(true, data.email ?: "", data.userId, data.userName ?: "", loginType = "normal")
                delay(300)
                Toast.makeText(context, "Welcome, ${data.userName ?: "User"}!", Toast.LENGTH_LONG).show()
                navController.navigate(BottomBarScreen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
                authViewModel.resetAuthState()
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG).show()
                authViewModel.resetAuthState()
            }
            else -> {}
        }
    }

    val handleLogin = {
        var isValid = true

        if (email.value.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            emailError.value = "Enter valid email"
            isValid = false
        } else {
            emailError.value = null
        }

        if (password.value.isBlank() || password.value.length < 6) {
            passwordError.value = "Minimum 6 characters"
            isValid = false
        } else {
            passwordError.value = null
        }

        if (isValid) {
            authViewModel.loginWithEmail(email.value.trim(), password.value.trim())
        }
    }

    LaunchedEffect(email.value, password.value) {
        buttonScale.animateTo(1.1f, tween(300, easing = FastOutSlowInEasing))
        buttonScale.animateTo(1f, tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CurvedTopBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 240.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginForm(
                email = email.value,
                onEmailChange = { email.value = it },
                password = password.value,
                onPasswordChange = { password.value = it },
                onLoginClick = handleLogin,
                onGoogleSignInClick = {
                    googleSignInLoading.value = true
                    googleSignInLauncher.launch(authViewModel.getGoogleSignInIntent())
                },
                onSignUpClick = { navController.navigate(Screen.Register.route) },
                buttonScale = buttonScale.value,
                emailError = emailError.value,
                passwordError = passwordError.value
            )
        }
    }

    if (authState is AuthState.Loading || googleSignInLoading.value) {
        LoadingDialog()
    }
}