package com.example.foodhub.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    userSessionManager: UserSessionManager,
    authViewModel: AuthViewModel = viewModel()
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val buttonScale = remember { Animatable(1f) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val authState by authViewModel.authState.collectAsState()
    val googleSignInLoading = remember { mutableStateOf(false) }

    val validEmail = "test@foodhub.com"
    val validPassword = "123456"

    // Google launcher
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

    // Init Google Client
    LaunchedEffect(Unit) {
        authViewModel.initGoogleSignIn(context)
    }

    // Auth state handling
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                val data = authState as AuthState.Success
                userSessionManager.saveLoginStatus(true, data.email ?: "", data.userId, data.userName ?: "",loginType = "google" )
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

    // Fake login check
    val handleLogin = {
        if (email.value == validEmail && password.value == validPassword) {
            coroutineScope.launch {
                userSessionManager.saveLoginStatus(true, email.value, "user123", "testName",loginType = "normal" )
            }
            navController.navigate(BottomBarScreen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        } else {
            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

    // Animate button
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
                .padding(start = 32.dp, top = 250.dp, end = 32.dp, bottom = 0.dp),
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
                buttonScale = buttonScale.value
            )
        }
    }

    if (authState is AuthState.Loading || googleSignInLoading.value) {
        LoadingDialog()
    }
}