package com.igordudka.mylove.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


object MyLovePalette{
    val Blue = Color(0xFF1875FF)
    val LightBlue = Color(0xFF8AF1FF)
    val Pink = Color(0xFFB5C500)
    val Violet = Color(0xFFFF2DC4)
    val DarkThemeGray = Color(0xFF757575)
    val DarkTextField = Color(0xFF2E2E2E)
    val DarkGray = Color(0xFFE6E6E6)
    val TimeText = Color(0xFF151515)
    val White = Color.White
    val Black = Color.Black
}

interface MyLoveColorScheme{
    val background: Color
    val userText: Color
    val botText: Color
    val surfaceColor: Color
    val secondaryColor: Color
    val firstGradient: Color
    val secondGradient: Color
}