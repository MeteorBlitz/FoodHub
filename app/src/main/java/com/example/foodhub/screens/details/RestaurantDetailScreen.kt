package com.example.foodhub.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
    val restaurant = viewModel.restaurants.collectAsState().value.find { it.id == restaurantId }
    val cartItems = viewModel.cartItems.collectAsState().value

    LaunchedEffect(restaurantId) {
        viewModel.fetchRestaurants()
        viewModel.loadCartItems()
    }

    restaurant?.let { rest ->
        Column(modifier = Modifier.fillMaxSize()) {
            RestaurantDetailTopBar(
                cartCount = cartItems.sumOf { it.quantity },
                onBackClick = { navController.popBackStack() },
                onCartClick = { /* TODO: Navigate to Cart */ }
            )

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                AsyncImage(
                    model = rest.image_url,
                    contentDescription = rest.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Text(
                    text = rest.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Rating: ${rest.rating} | Location: ${rest.location}",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                rest.menu.forEach { menuItem ->
                    val quantity = cartItems.find { it.name == menuItem.name }?.quantity ?: 0
                    MenuItemRow(
                        item = CartItem(name = menuItem.name, price = menuItem.price.toDouble()),
                        quantity = quantity,
                        onAddClick = {
                            viewModel.addToCart(
                                CartItem(name = menuItem.name, price = menuItem.price.toDouble())
                            )
                        },
                        onRemoveClick = {
                            viewModel.decreaseCartItemQuantity(menuItem.name)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CartSection(
                    cartItems = cartItems,
                    onRemove = { viewModel.removeFromCart(it.id) }
                )
            }
        }
    }
}