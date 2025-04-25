package com.example.foodhub.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.foodhub.data.local.CartItem

@Composable
fun MenuItemRow(
    item: CartItem,
    quantity: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "₹${item.price}", color = Color.Gray)
        }
        Row {
            IconButton(onClick = onRemoveClick, enabled = quantity > 0) {
                Icon(Icons.Default.RemoveCircle, contentDescription = "Remove")
            }
            Text(
                text = quantity.toString(),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.AddCircle, contentDescription = "Add")
            }
        }
    }
}