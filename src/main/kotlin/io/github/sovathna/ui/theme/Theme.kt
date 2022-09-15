package io.github.sovathna.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font


private val LightColors = lightColors(
    onError = md_theme_light_onError,
    error = md_theme_light_error,
    onBackground = md_theme_light_onBackground,
    background = md_theme_light_background,
    onSurface = md_theme_light_onSurface,
    surface = md_theme_light_surface,
    onSecondary = md_theme_light_onSecondary,
    secondary = md_theme_light_secondary,
    secondaryVariant = md_theme_light_secondaryContainer,
    onPrimary = md_theme_light_onPrimary,
    primary = md_theme_light_primary,
    primaryVariant = md_theme_light_primaryContainer
)


private val DarkColors = darkColors(
    primary = md_theme_dark_primary,
    primaryVariant = md_theme_dark_primaryContainer,
    secondary = md_theme_dark_secondary,
    secondaryVariant = md_theme_dark_secondaryContainer,
    onError = md_theme_dark_onError,
    error = md_theme_dark_error,
    onBackground = md_theme_dark_onBackground,
    background = md_theme_dark_background,
    onSurface = md_theme_dark_onSurface,
    surface = md_theme_dark_surface,
    onSecondary = md_theme_dark_onSecondary,
    onPrimary = md_theme_dark_onPrimary,
)

private val Typography = Typography(
    FontFamily(
        Font("NotoSerifKhmer-Regular.ttf"),
        Font("NotoSerifKhmer-Bold.ttf", FontWeight.Bold),
        Font("NotoSerifKhmer-Light.ttf", FontWeight.Light)
    )
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors =
        if (!useDarkTheme) {
            LightColors
        } else {
            DarkColors
        }

    MaterialTheme(
        typography = Typography,
        colors = colors,
        content = content
    )
}