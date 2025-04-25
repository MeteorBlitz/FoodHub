package com.example.foodhub.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodhub.data.local.CartItem
import com.example.foodhub.ui.viewmodel.RestaurantViewModel

@Composable
fun RestaurantDetailScreen(
    navController: NavController,
    restaurantId: Int,
    viewModel: RestaurantViewModel = hiltViewModel()
) {

    // Fetch restaurant data and menu items
    val restaurant = viewModel.restaurants.collectAsState().value.find { it.id == restaurantId }
    val cartItems = viewModel.cartItems.collectAsState().value

    // Fetch restaurant data if not available
    LaunchedEffect(restaurantId) {
        viewModel.fetchRestaurants()
        viewModel.loadCartItems()
    }

    restaurant?.let { rest ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Display restaurant image
            AsyncImage(
                model = rest.image_url,
                contentDescription = rest.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            // Display restaurant info
            Text(
                text = rest.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Rating: ${rest.rating} | Location: ${rest.location}",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Display menu items
            rest.menu.forEach { menuItem ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = menuItem.name, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "₹${menuItem.price}", style = MaterialTheme.typography.bodySmall)

                    // Add to cart button
                    Button(
                        onClick = {
                            viewModel.addToCart(
                                CartItem(name = menuItem.name, price = menuItem.price.toDouble())
                            )
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = "Add to Cart")
                    }
                }
            }

            // Display current cart items
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your Cart",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            cartItems.forEach { cartItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${cartItem.name} x${cartItem.quantity}",
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { viewModel.removeFromCart(cartItem.id) },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "Remove")
                    }
                }
            }
        }
    }
}