package com.example.foodhub.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailTopBar(
    cartCount: Int,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Restaurant Details") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            Box {
                IconButton(onClick = onCartClick) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                }
                if (cartCount > 0) {
                    Box(
                        modifier = Modifier
                            .offset(x = 10.dp, y = (-5).dp)
                            .size(16.dp)
                            .background(Color.Red, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    )
}