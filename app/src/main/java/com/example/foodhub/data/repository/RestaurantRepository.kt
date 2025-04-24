package com.example.foodhub.data.repository

import com.example.foodhub.data.model.Restaurant
import com.example.foodhub.data.remote.RestaurantApiService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val apiService: RestaurantApiService
) {
    suspend fun getRestaurants(): List<Restaurant> = apiService.getRestaurants()
}