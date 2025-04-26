package com.example.foodhub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodhub.data.local.UserSessionManager

class ProfileViewModelFactory(
    private val userSessionManager: UserSessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(userSessionManager) as T
    }
}