package com.example.foodhub.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.R
import com.example.foodhub.data.local.UserSessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel(
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    // Logs whenever login status changes
    init {
        viewModelScope.launch {
            userSessionManager.isLoggedInFlow.collect { isLoggedIn ->
                Log.d("ProfileViewModel", "isLoggedIn ViewModel: $isLoggedIn")
            }
        }
    }

    // Expose session details to UI
    val isLoggedIn = userSessionManager.isLoggedInFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    val userName = userSessionManager.savedNameFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ""
    )

    val userEmail = userSessionManager.savedEmailFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ""
    )

    /**
     * Handles both Google and normal sign-out
     */
    fun logout(context: Context) {
        viewModelScope.launch {
            val loginType = userSessionManager.loginTypeFlow.stateIn(
                viewModelScope, SharingStarted.Eagerly, "normal"
            ).value

            if (loginType == "google") {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                googleSignInClient.signOut().await()
                FirebaseAuth.getInstance().signOut()
            }

            userSessionManager.logout()
        }
    }
}
