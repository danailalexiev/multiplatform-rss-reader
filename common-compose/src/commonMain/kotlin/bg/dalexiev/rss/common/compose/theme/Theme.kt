package com.infinitelambda.app.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.infinitelambda.app.kmm.android.ui.theme.*

private val DarkColorPalette = darkColors(
    primary = Black200,
    primaryVariant = Black700,
    secondary = Green,
    background = Black700,
    surface = Black200,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
    error = Red
)

private val LightColorPalette = lightColors(
    primary = Black500,
    primaryVariant = Color.Black,
    secondary = Green,
    background = White,
    surface = Grey,
    onPrimary = White,
    onSecondary = Black700,
    onBackground = Black700,
    onSurface = Black700,
    error = Red
)

@Composable
fun RssReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}