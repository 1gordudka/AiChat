package com.igordudka.mylove.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.igordudka.mylove.presentation.home.settings.SettingsViewModel

object MyLoveLightColorsScheme : MyLoveColorScheme{
    override val background: Color
        get() = MyLovePalette.White
    override val botText: Color
        get() = MyLovePalette.White
    override val surfaceColor: Color
        get() = MyLovePalette.DarkGray
    override val userText: Color
        get() = MyLovePalette.Black
    override val secondaryColor: Color
        get() = MyLovePalette.TimeText
    override val firstGradient: Color
        get() = MyLovePalette.Blue
    override val secondGradient: Color
        get() = MyLovePalette.LightBlue
}

object MyLoveDarkColorsScheme : MyLoveColorScheme{
    override val background: Color
        get() = MyLovePalette.Black
    override val botText: Color
        get() = MyLovePalette.Black
    override val surfaceColor: Color
        get() = MyLovePalette.DarkTextField
    override val userText: Color
        get() = MyLovePalette.White
    override val secondaryColor: Color
        get() = MyLovePalette.DarkThemeGray
    override val firstGradient: Color
        get() = MyLovePalette.Pink
    override val secondGradient: Color
        get() = MyLovePalette.Violet
}

val DarkColorScheme = darkColorScheme(
    background = MyLoveDarkColorsScheme.background,
    surface = MyLoveDarkColorsScheme.surfaceColor,
    onBackground = MyLoveDarkColorsScheme.userText,
    onSecondary = MyLoveDarkColorsScheme.userText,
    onSurface = MyLoveDarkColorsScheme.secondaryColor,
    tertiary = MyLoveDarkColorsScheme.firstGradient,
    onTertiary = MyLoveDarkColorsScheme.secondGradient
)
val LightColorScheme = lightColorScheme(
    background = MyLoveLightColorsScheme.background,
    surface = MyLoveLightColorsScheme.surfaceColor,
    onBackground = MyLoveLightColorsScheme.userText,
    onSecondary = MyLoveLightColorsScheme.userText,
    onSurface = MyLoveLightColorsScheme.secondaryColor,
    tertiary = MyLoveLightColorsScheme.firstGradient,
    onTertiary = MyLoveLightColorsScheme.secondGradient
)


@Composable
fun MyLoveTheme(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    darkTheme: Boolean = settingsViewModel.isDarkTheme.collectAsState().value == true || isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    systemUiController.setSystemBarsColor(if (darkTheme) Color.Black else Color.White)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ){
        content()
    }
}