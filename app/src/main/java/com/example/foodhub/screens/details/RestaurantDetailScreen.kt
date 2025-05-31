package com.example.foodhub.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodhub.data.local.CartItem
import com.example.foodhub.ui.viewmodel.RestaurantViewModel
import com.example.foodhub.util.UiState
import kotlinx.coroutines.launch

@Composable
fun RestaurantDetailScreen(
    navController: NavController,
    restaurantId: Int,
    viewModel: RestaurantViewModel = hiltViewModel()
) {
    // Observe the UiState of restaurants list
    val restaurantState = viewModel.restaurants.collectAsState()

    val cartItems = viewModel.cartItems.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val addedItemList = remember { mutableStateListOf<String>() }

    LaunchedEffect(restaurantId) {
        viewModel.fetchRestaurants()
        viewModel.loadCartItems()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = restaurantState.value) {
            is UiState.Loading -> {
                // Show loading indicator
                androidx.compose.material3.CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Success -> {
                // Find restaurant by id in the success data
                val restaurant = state.data.find { it.id == restaurantId }

                if (restaurant == null) {
                    // Show not found message
                    Text(
                        text = "Restaurant not found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    // Show restaurant details UI (your existing UI)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 60.dp)
                    ) {
                        RestaurantDetailTopBar(
                            cartCount = cartItems.sumOf { it.quantity },
                            onBackClick = { navController.popBackStack() },
                            onCartClick = { /* TODO: Navigate to Cart */ }
                        )

                        AsyncImage(
                            model = restaurant.image_url,
                            contentDescription = restaurant.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )

                        Text(
                            text = restaurant.name,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = "Rating: ${restaurant.rating} | Location: ${restaurant.location}",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        restaurant.menu.forEach { menuItem ->
                            val quantity = cartItems.find { it.name == menuItem.name }?.quantity ?: 0
                            MenuItemRow(
                                item = CartItem(name = menuItem.name, price = menuItem.price.toDouble()),
                                quantity = quantity,
                                onAddClick = {
                                    viewModel.addToCart(
                                        CartItem(name = menuItem.name, price = menuItem.price.toDouble())
                                    )
                                    if (!addedItemList.contains(menuItem.name)) {
                                        addedItemList.add(menuItem.name)
                                        scope.launch {
                                            snackbarHostState.showSnackbar("${menuItem.name} added to cart")
                                        }
                                    }
                                },
                                onRemoveClick = {
                                    viewModel.decreaseCartItemQuantity(menuItem.name)

                                    val newQty = cartItems.find { it.name == menuItem.name }?.quantity ?: 0
                                    if (newQty <= 1) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("${menuItem.name} removed from cart")
                                        }
                                        addedItemList.remove(menuItem.name)
                                    }
                                },
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        CartSection(
                            cartItems = cartItems,
                            onRemove = { item->
                                viewModel.removeFromCart(item.id)
                                scope.launch {
                                    snackbarHostState.showSnackbar("${item.name} removed from cart")
                                }
                            }
                            ,
                            onItemRemovedMessage = { itemName ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("$itemName removed from cart")
                                }
                                addedItemList.remove(itemName)
                            }
                        )
                    }
                }
            }

            is UiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

