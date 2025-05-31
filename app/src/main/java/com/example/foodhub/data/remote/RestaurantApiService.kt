package com.example.foodhub.data.remote

import com.example.foodhub.data.model.Restaurant
import retrofit2.http.GET

interface RestaurantApiService {
    @GET("37a1018e-ff45-4514-87c9-049252d728c2")
    suspend fun getRestaurants(): List<Restaurant>
}