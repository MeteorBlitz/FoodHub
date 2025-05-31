package com.example.foodhub.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: String, val email: String?, val userName: String?) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private lateinit var googleSignInClient: GoogleSignInClient

    fun initGoogleSignIn(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getGoogleSignInIntent(): Intent {
        _authState.value = AuthState.Loading
        return googleSignInClient.signInIntent
    }

    fun handleGoogleSignInResult(data: Intent?) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                val firebaseAuthResult = auth.signInWithCredential(credential).await()

                firebaseAuthResult.user?.let { user ->
                    val userId = user.uid
                    val email = user.email
                    val userName = user.displayName

                    Log.d("AuthViewModel", "Google Sign-In successful: User ID = $userId")
                    _authState.value = AuthState.Success(userId, email, userName)
                } ?: run {
                    _authState.value = AuthState.Error("Firebase sign-in user is null.")
                    Log.e("AuthViewModel", "Firebase sign-in user is null.")
                }

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign-In error: ${e.message}")
                _authState.value = AuthState.Error("Sign-in failed: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                auth.signOut()
                googleSignInClient.signOut().await()
                _authState.value = AuthState.Idle
                Log.d("AuthViewModel", "User signed out successfully.")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign-Out error: ${e.message}")
                _authState.value = AuthState.Error("Sign out failed: ${e.message ?: "Unknown error"}")
            }
        }
    }
}
