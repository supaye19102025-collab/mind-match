package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CosmicColorScheme = darkColorScheme(
    primary = CosmicPrimary,
    secondary = CosmicSecondary,
    background = CosmicBackground,
    surface = CosmicSurface,
    onPrimary = CosmicOnPrimary,
    onBackground = Color(0xFFE2E1F6),
    onSurface = Color(0xFFE2E1F6),
    tertiary = CosmicAccent
)

private val SunsetColorScheme = lightColorScheme(
    primary = SunsetPrimary,
    secondary = SunsetSecondary,
    background = SunsetBackground,
    surface = SunsetSurface,
    onPrimary = SunsetOnPrimary,
    onBackground = Color(0xFF2C150E),
    onSurface = Color(0xFF2C150E),
    tertiary = SunsetAccent
)

private val ForestColorScheme = lightColorScheme(
    primary = ForestPrimary,
    secondary = ForestSecondary,
    background = ForestBackground,
    surface = ForestSurface,
    onPrimary = ForestOnPrimary,
    onBackground = Color(0xFF0F1E14),
    onSurface = Color(0xFF0F1E14),
    tertiary = ForestAccent
)

private val RoyalColorScheme = darkColorScheme(
    primary = RoyalPrimary,
    secondary = RoyalSecondary,
    background = RoyalBackground,
    surface = RoyalSurface,
    onPrimary = RoyalOnPrimary,
    onBackground = Color(0xFFFFF2D4),
    onSurface = Color(0xFFFFF2D4),
    tertiary = RoyalAccent
)

@Composable
fun MindMatchTheme(
    themeName: String = "Cosmic Dark",
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeName) {
        "Cosmic Dark" -> CosmicColorScheme
        "Sunset Peach" -> SunsetColorScheme
        "Forest Breeze" -> ForestColorScheme
        "Royal Velvet" -> RoyalColorScheme
        else -> CosmicColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
