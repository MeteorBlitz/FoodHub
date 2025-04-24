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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodhub.components.RestaurantCard
import com.example.foodhub.navigation.Screen
import com.example.foodhub.ui.theme.FoodHubTheme
import com.example.foodhub.ui.viewmodel.RestaurantViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: RestaurantViewModel = hiltViewModel()) {
    // Observe the restaurant data from the ViewModel
    val restaurantList = viewModel.restaurants.collectAsState(emptyList())
    val isLoading = restaurantList.value.isEmpty()

    // Fetch data once when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.fetchRestaurants()
    }

    // State to handle refresh status
    var isRefreshing = remember { mutableStateOf(false) }

    // Pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
        }
    )

    // Handle refresh effect properly
    if (isRefreshing.value) {
        LaunchedEffect(Unit) {
            delay(1500) // Simulate network or data refresh
            isRefreshing.value = false
        }
    }

    // Show CircularProgressIndicator if data is loading
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF6200EE),
                strokeWidth = 6.dp
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Title Text
                Text(
                    text = "Restaurants",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )

                // LazyColumn for restaurant list
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    items(restaurantList.value) { restaurant ->
                        RestaurantCard(restaurant = restaurant) {
                            navController.navigate("${Screen.RestaurantDetail.route}/${restaurant.id}")
                        }
                    }
                }
            }

            // Pull-to-refresh indicator
            PullRefreshIndicator(
                refreshing = isRefreshing.value,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FoodHubTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}