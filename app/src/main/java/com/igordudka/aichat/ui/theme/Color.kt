package com.igordudka.aichat.ui.theme

import androidx.compose.ui.graphics.Color


object MyLovePalette{
    val Blue = Color(0xFFFF18BE)
    val LightBlue = Color(0xFFF368FF)
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