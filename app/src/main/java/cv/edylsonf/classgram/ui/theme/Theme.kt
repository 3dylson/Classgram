package cv.edylsonf.classgram.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryColor200,
    primaryVariant =  primaryDarkColor,

    secondary = secondaryColor,
    secondaryVariant = secondaryColor,

    background = darkThemeBlack,
    surface = darkThemeBlack,

    error = darkThemeError,

    onPrimary = primaryTextColor,
    onSecondary = secondaryTextColor,

    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant =  primaryDarkColor,

    secondary = secondaryColor,
    secondaryVariant = secondaryDarkColor,
)

@Composable
fun ClassgramTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit){
    var colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        content = content
    )
}