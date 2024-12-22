package com.example.shabreenmohammed_comp304sec002_lab1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes

// Define the custom colors
val LightPurple = Color(0xFFE6D4F7)
val LightBlue = Color(0xFFCFE9E8)
val LightPeach = Color(0xFFFDE7C9)
val DarkGrey = Color(0xFF2F2F2F)

// Define Dark Color Palette
private val DarkColorPalette = darkColorScheme(
    primary = DarkGrey,         // Use dark grey as primary in dark mode
    onPrimary = Color.White,
    secondary = LightBlue,      // Use light blue for accents in dark mode
    onSecondary = DarkGrey,
    background = DarkGrey,
    onBackground = Color.White,
    surface = DarkGrey,
    onSurface = Color.White,
    error = Color(0xFFCF6679),  // Error color
    onError = Color.White
)

// Define Light Color Palette using your custom palette
private val LightColorPalette = lightColorScheme(
    primary = LightPurple,      // Light purple for primary color
    onPrimary = DarkGrey,       // Text color on primary
    secondary = LightBlue,      // Light blue for accents
    onSecondary = DarkGrey,     // Text on secondary
    background = LightPeach,    // Light peach as background
    onBackground = DarkGrey,    // Text on background
    surface = LightBlue,       // Use same color for surfaces (like cards)
    onSurface = DarkGrey,       // Text on surface
    error = Color(0xFFB00020),  // Error color
    onError = Color.White
)

// Default typography and shapes (you can customize these if needed)
private val DefaultTypography = Typography()
private val DefaultShapes = Shapes()

@Composable
fun ShabreenMohammed_Comp304Sec002_Lab1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = DefaultTypography,
        shapes = DefaultShapes,
        content = content
    )
}
