package com.example.foodhub.screens

import android.content.Context

fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putBoolean("is_logged_in", isLoggedIn)
        apply()
    }
}

fun getLoginStatus(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("is_logged_in", false)
}