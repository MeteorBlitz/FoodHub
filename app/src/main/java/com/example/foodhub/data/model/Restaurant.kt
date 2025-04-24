package com.example.foodhub.data.model

data class Restaurant(
    val delivery_charge: Int,
    val id: Int,
    val image_url: String,
    val location: String,
    val menu: List<Menu>,
    val name: String,
    val rating: Double,
    val timing: String
)