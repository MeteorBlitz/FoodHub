package com.example.foodhub.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodhub.R

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp), // smaller size, circle shape
        shape = RoundedCornerShape(24.dp), // circle because half of size
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Logo",
            modifier = Modifier.size(28.dp) // bigger icon size
        )
    }
}


