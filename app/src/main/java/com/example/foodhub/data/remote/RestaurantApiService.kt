package com.example.foodhub.data.remote

import com.example.foodhub.data.model.Restaurant
import retrofit2.http.GET

interface RestaurantApiService {
    @GET("d91d12f6-a455-407c-8c72-629cbce4bfd6")
    suspend fun getRestaurants(): List<Restaurant>
}