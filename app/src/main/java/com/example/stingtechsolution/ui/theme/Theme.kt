package com.example.stingtechsolution.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StingLightColors = lightColorScheme(
    primary = StingBlue,
    onPrimary = Color.White,

    primaryContainer = StingOrangeLight,
    onPrimaryContainer = StingBlueDark,

    secondary = StingOrange,
    onSecondary = Color.White,

    background = StingBackground,
    onBackground = StingOnSurface,

    surface = StingSurface,
    onSurface = StingOnSurface,

    surfaceVariant = Color(0xFFF3F4F8),
    onSurfaceVariant = StingGray,

    outline = Color(0xFFD1D5DB)
)

@Composable
fun StingTechSolutionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = StingLightColors,
        typography = Typography,
        content = content
    )
}
