package com.example.foodhub.data.remote

import com.example.foodhub.data.model.Restaurant
import retrofit2.http.GET

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): List<Restaurant>
}