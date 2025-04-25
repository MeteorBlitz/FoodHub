package com.example.foodhub.data.remote

import com.example.foodhub.data.model.Restaurant
import retrofit2.http.GET

interface RestaurantApiService {
    //@GET("d91d12f6-a455-407c-8c72-629cbce4bfd6")
    @GET("2fe64a12-28f8-468d-927a-f81eef5d35a5")
    suspend fun getRestaurants(): List<Restaurant>
}