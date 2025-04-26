package com.example.foodhub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.local.UserSessionManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val userSessionManager: UserSessionManager) : ViewModel() {

    // Log the changes to `isLoggedIn`
    init {
        userSessionManager.isLoggedInFlow
            .onEach{ isLoggedIn ->
                Log.d("ProfileViewModel", "isLoggedIn Viewmodel: $isLoggedIn")  // Log each update
            }
            .launchIn(viewModelScope)  // Launch the flow collection in the ViewModel scope
    }

    // Expose login status as StateFlow
    val isLoggedIn = userSessionManager.isLoggedInFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )


    val userName = userSessionManager.savedNameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val userEmail = userSessionManager.savedEmailFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    fun logout() {
        viewModelScope.launch {
            userSessionManager.logout()
        }
    }
}