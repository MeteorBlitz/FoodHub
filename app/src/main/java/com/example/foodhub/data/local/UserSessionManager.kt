package com.example.foodhub.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_session")

class UserSessionManager(context: Context) {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val EMAIL = stringPreferencesKey("email")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    // Save login status, email, and userId
    suspend fun saveLoginStatus(isLoggedIn: Boolean, email: String, userId: String, userName: String) {
        Log.d("UserSessionManager", "Saving login status to DataStore: $isLoggedIn")
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = isLoggedIn
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.USER_NAME] = userName
        }
    }

    // Get login status
    val isLoggedInFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] == true
        }

    // Get saved email
    val savedEmailFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EMAIL] ?: ""
        }

    // Get saved user ID
    val savedUserIdFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_ID] ?: ""
        }

    // Get saved name
    val savedNameFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_NAME] ?: ""
        }

    // Clear user session on logout
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = false
            preferences.remove(PreferencesKeys.EMAIL)
            preferences.remove(PreferencesKeys.USER_ID)
            preferences.remove(PreferencesKeys.USER_NAME)
        }
    }
}