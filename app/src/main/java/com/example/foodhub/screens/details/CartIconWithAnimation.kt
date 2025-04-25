package com.example.foodhub.screens.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun CartIconWithAnimation(cartCount: Int) {
    // Remember to maintain scale state
    var animatedScale = remember { mutableFloatStateOf(1f) }

    // Animate the cart icon when cartCount changes
    LaunchedEffect(cartCount) {
        animatedScale.floatValue = 1.2f
        delay(300) // scale effect lasts for 300 ms
        animatedScale.floatValue = 1f
    }

    IconButton(onClick = { /* Open Cart Screen or Action */ }) {
        BadgedBox(
            modifier = Modifier,
            badge = {
                if (cartCount > 0) {
                    Badge(
                        content = {
                            Text(text = "$cartCount", color = Color.White) // Show the cart count
                        },
                        modifier = Modifier.padding(4.dp) // Optionally adjust badge position
                    )
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart, // Correct use of Icons
                contentDescription = "Cart",
                modifier = Modifier.scale(animatedScale.floatValue) // Use animatedScale value here
            )
        }
    }
}