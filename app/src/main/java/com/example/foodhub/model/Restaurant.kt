package com.example.foodhub.model

data class Restaurant(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val rating: Double
)