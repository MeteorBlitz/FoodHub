package com.example.foodhub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {

    // Add an item to the cart
    @Insert
    suspend fun addToCart(cartItem: CartItem)

    // Update the cart item (for example, when the quantity changes)
    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    // Get all items in the cart
    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItem>

    @Query("SELECT * FROM cart_items WHERE name = :name LIMIT 1")
    suspend fun getCartItemByName(name: String): CartItem?


    // Delete an item from the cart using its ID
    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun removeFromCart(cartItemId: Int)

    // Delete all items from the cart (clear the cart)
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}