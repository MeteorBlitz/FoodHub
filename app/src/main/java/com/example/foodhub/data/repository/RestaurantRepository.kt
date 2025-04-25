package com.example.foodhub.data.repository

import com.example.foodhub.data.local.CartDao
import com.example.foodhub.data.local.CartItem
import com.example.foodhub.data.model.Restaurant
import com.example.foodhub.data.remote.RestaurantApiService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val apiService: RestaurantApiService,
    private val cartDao: CartDao
) {
    suspend fun getRestaurants(): List<Restaurant> = apiService.getRestaurants()

    // Add to cart
    suspend fun addToCart(cartItem: CartItem) {
        cartDao.addToCart(cartItem)
    }

    // Update cart item (e.g., changing quantity)
    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    // Get all items in the cart
    suspend fun getCartItems(): List<CartItem> {
        return cartDao.getAllCartItems()
    }

    // Remove an item from the cart by its ID
    suspend fun removeFromCart(cartItemId: Int) {
        cartDao.removeFromCart(cartItemId)
    }

    // Clear all items in the cart
    suspend fun clearCart() {
        cartDao.clearCart()
    }
}