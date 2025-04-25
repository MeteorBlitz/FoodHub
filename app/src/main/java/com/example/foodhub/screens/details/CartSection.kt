package com.example.foodhub.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodhub.data.local.CartItem


@Composable
fun CartSection(
    cartItems: List<CartItem>,
    onRemove: (CartItem) -> Unit,
    onItemRemovedMessage: ((String) -> Unit)? = null // optional callback
) {
    Column {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        cartItems.forEach { cartItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${cartItem.name} x${cartItem.quantity}",
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    onRemove(cartItem)
                    onItemRemovedMessage?.invoke(cartItem.name) //  invoke snackbar from parent
                }) {
                    Text("Remove")
                }
            }
        }
    }
}