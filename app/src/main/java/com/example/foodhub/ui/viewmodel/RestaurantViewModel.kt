package com.example.foodhub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.model.Restaurant
import com.example.foodhub.data.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants


    // Fetch restaurants from the repository
    fun fetchRestaurants() {
        viewModelScope.launch {
            try {
                val restaurantList = repository.getRestaurants()
                _restaurants.value = restaurantList
                Log.d("DEBUG", "Fetched restaurants: ${restaurantList.size}")
            } catch (e: Exception) {
                // Handle the error
                Log.e("ERROR", "Failed to fetch restaurants: ${e.message}")
            }
        }
    }
}