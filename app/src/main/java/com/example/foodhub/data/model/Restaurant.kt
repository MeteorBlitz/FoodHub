package com.example.foodhub.data.model


data class Restaurant(
    val id: Int,
    val name: String,
    val rating: Double,
    val location: String,
    val image_url: String,
    val timing: String,
    val delivery_charge: Int,
    val category: String,
    val menu: List<MenuItem>
)