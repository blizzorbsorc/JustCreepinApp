package com.example.justcreepinapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 👇 Define your color schemes here 👇

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    // For dark mode, use a dark gradient
    background = DeepPurple,
    // Text on top of the background should be light
    onBackground = LightText,
    // Card color
    surface = PurpleGrey40,
    // Text for cards
    onSurface = LightText
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    // For light mode, use a light gradient background
    background = LightPurple,
    // Text on top of the background should be dark
    onBackground = DarkText,
    // Card color
    surface = Color.White,
    // Text for cards
    onSurface = DarkText
)

// We need to extend MaterialTheme to include our custom gradient
object AppTheme {
    val gradient: Brush
        @Composable
        get() = if (isSystemInDarkTheme()) {
            Brush.linearGradient(colors = listOf(DeepPurple, Color.Black))
        } else {
            Brush.linearGradient(colors = listOf(LightPurple, SoftPink, DeepPurple))
        }
}

@Composable
fun JustCreepinAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // This part handles the status bar color, it's good practice
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
