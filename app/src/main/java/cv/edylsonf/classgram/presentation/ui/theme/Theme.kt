package cv.edylsonf.classgram.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Cyan_200,
    primaryVariant = primaryDarkColor,
    secondary = secondaryColor,
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = primaryDarkColor,
    secondary = secondaryColor,

    onBackground = Color.Black,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,

    onSurface = Color.Black,
    */
)

@Composable
fun ClassgramTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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