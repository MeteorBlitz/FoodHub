package com.example.foodhub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodhub.components.RestaurantCard
import com.example.foodhub.navigation.Screen
import com.example.foodhub.ui.viewmodel.RestaurantViewModel
import com.example.foodhub.util.UiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: RestaurantViewModel = hiltViewModel()) {
    // Observe the UiState from the ViewModel
    val restaurantState = viewModel.restaurants.collectAsState()

    // State to handle refresh status
    var isRefreshing = remember { mutableStateOf(false) }

    // Pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            viewModel.fetchRestaurants()
        }
    )

    // Call fetchRestaurants when screen loads first time
    LaunchedEffect(Unit) {
        viewModel.fetchRestaurants()
    }

    // Handle refresh effect properly
    if (isRefreshing.value) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            isRefreshing.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        when (val state = restaurantState.value) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    color = Color(0xFF6200EE),
                    strokeWidth = 6.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Success -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Restaurants",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(state.data) { restaurant ->
                            RestaurantCard(restaurant = restaurant) {
                                navController.navigate("${Screen.RestaurantDetail.route}/${restaurant.id}")
                            }
                        }
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

        PullRefreshIndicator(
            refreshing = isRefreshing.value,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}



