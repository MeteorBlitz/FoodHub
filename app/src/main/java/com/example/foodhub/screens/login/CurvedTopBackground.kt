package com.example.foodhub.screens.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun CurvedTopBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height * 0.7f)
                quadraticTo(width / 2, height * 1.2f, width, height * 0.7f)
                lineTo(width, 0f)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6200EE), Color(0xFFBB86FC))
                )
            )
        }

        Text(
            text = "Welcome Back!",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 50.dp)
        )
    }
}