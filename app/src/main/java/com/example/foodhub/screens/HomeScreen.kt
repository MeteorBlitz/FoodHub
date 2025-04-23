package com.example.foodhub.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.foodhub.components.RestaurantCard
import com.example.foodhub.model.RestaurantData
import com.example.foodhub.ui.theme.FoodHubTheme

@Composable
fun HomeScreen(navController: NavController) {
    val restaurantList = RestaurantData.restaurantList

    Column {
        Text(
            text = "Restaurants",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {
            items(restaurantList) { restaurant ->
                RestaurantCard(restaurant = restaurant) {
                    navController.navigate("restaurantDetail/${restaurant.id}")
                }
            }
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