package com.example.foodhub.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


val FoodHubLogo: ImageVector
    get() = ImageVector.Builder(
        name = "FoodHubLogo", defaultWidth = 48.dp, defaultHeight = 48.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).apply {
        // Plate (circle)
        path(
            fill = SolidColor(Color(0xFF6200EE)),
            pathFillType = PathFillType.NonZero
        ) {
            arcTo(2f, 2f, 0f, true, true, 22f, 12f)
            arcTo(10f, 10f, 0f, true, true, 2f, 12f)
            close()
        }

        // Fork
        path(
            fill = SolidColor(Color.White),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(7f, 7f)
            verticalLineTo(17f)
            horizontalLineTo(8.5f)
            verticalLineTo(7f)
            lineTo(7f, 7f)
        }

        // Sparkle (like a chef's sparkle)
        path(
            fill = SolidColor(Color.White),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(16f, 5f)
            lineTo(17f, 8f)
            lineTo(20f, 9f)
            lineTo(17f, 10f)
            lineTo(16f, 13f)
            lineTo(15f, 10f)
            lineTo(12f, 9f)
            lineTo(15f, 8f)
            close()
        }
    }.build()