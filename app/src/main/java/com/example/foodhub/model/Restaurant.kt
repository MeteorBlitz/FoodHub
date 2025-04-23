package com.example.foodhub.model

data class Restaurant(
    val id: Int,
    val name: String,
    val rating: Double,
    val location: String,
    val image_url: String
)