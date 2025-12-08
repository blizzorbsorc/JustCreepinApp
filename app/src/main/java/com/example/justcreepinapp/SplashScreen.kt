package com.example.justcreepinapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val isDarkTheme = isSystemInDarkTheme()

    LaunchedEffect(true) {
        delay(3000) // wait 3 sec (change this number for different duration)
        onTimeout()
    }

    // Gradient that adapts to theme - ACTUALLY DARK
    val gradientColors = if (isDarkTheme) {
        listOf(
            Color(0xFF0D0221), // Very dark purple - almost black
            Color(0xFF1A0B2E), // Deep dark purple
            Color(0xFF2D1B3D)  // Dark purple
        )
    } else {
        listOf(
            Color(0xFF6650a4), // Purple40
            Color(0xFFD0BCFF), // Purple80
            Color(0xFFEFB8C8)  // Pink80
        )
    }

    val gradient = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier.size(300.dp) // Adjust size here (default was no size specified)
            )
        }
    }
}