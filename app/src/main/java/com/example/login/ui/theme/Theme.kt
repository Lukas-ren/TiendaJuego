package com.example.login.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RojoPrimario,
    secondary = RojoSecundario,
    tertiary = RojoBrillante,
    background = NegroFondo,
    surface = GrisOscuro,
    onPrimary = TextoPrimario,
    onSecondary = TextoPrimario,
    onBackground = TextoPrimario,
    onSurface = TextoPrimario,
)

private val LightColorScheme = lightColorScheme(
    primary = RojoPrimario,
    secondary = RojoSecundario,
    tertiary = RojoBrillante,
    background = GrisMedio,
    surface = GrisOscuro,
    onPrimary = TextoPrimario,
    onSecondary = TextoPrimario,
    onBackground = TextoPrimario,
    onSurface = TextoPrimario,
)

@Composable
fun LoginTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}