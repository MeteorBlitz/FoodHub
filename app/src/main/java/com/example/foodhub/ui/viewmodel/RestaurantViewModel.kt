package com.example.foodhub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub.data.local.CartItem
import com.example.foodhub.data.model.Restaurant
import com.example.foodhub.data.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    // State to hold the list of cart items
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Fetch restaurants from the repository
    fun fetchRestaurants() {
        viewModelScope.launch {
            try {
                val restaurantList = repository.getRestaurants()
                _restaurants.value = restaurantList
                Log.d("DEBUG", "Fetched restaurants: ${restaurantList.size}")
            } catch (e: Exception) {
                // Handle the error
                Log.e("ERROR", "Failed to fetch restaurants: ${e.message}")
            }
        }
    }

    // Get all items in the cart
    fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getCartItems()
        }
    }

    // Add item to cart
    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.addToCart(cartItem)
            loadCartItems()  // Reload the cart items after adding
        }
    }

    // Update existing cart item
    fun updateCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            repository.updateCartItem(cartItem)
            loadCartItems()  // Reload the cart items after update
        }
    }


    // Remove item from the cart
    fun removeFromCart(cartItemId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(cartItemId)
            loadCartItems()  // Reload the cart items after removal
        }
    }

    // Clear the cart
    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            loadCartItems()  // Reload the cart items after clearing
        }
    }
}