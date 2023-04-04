package com.igordudka.aichat.ui.theme

import androidx.compose.ui.graphics.Color
import com.igordudka.aichat.R

val lightColorThemes =
    listOf(ColorTheme(Pair(Color(0xFFFB8CDC), Color(0xFFC27CC8)), R.drawable.send_icon_light1),
    ColorTheme(Pair(Color(0xFF94914C), Color(0xFFFDB16B)), R.drawable.send_icon_light2),
    ColorTheme(Pair(Color(0xFFC1FFA4), Color(0xFF91FF5E)), R.drawable.send_icon_light3),
    ColorTheme(Pair(Color(0xFF69FFFF), Color(0xFF535AFF)), R.drawable.send_icon_light4)
    )

val darkColorThemes = listOf(
    ColorTheme(Pair(Color(0xFF040D5A), Color(0xFF0D7FA4)), R.drawable.send_icon_dark1),
    ColorTheme(Pair(Color(0xFF051F00), Color(0xFF010901)), R.drawable.send_icon_dark2),
    ColorTheme(Pair(Color(0xFF4B0F0F), Color(0xFF170101)), R.drawable.send_icon_dark3),
    ColorTheme(Pair(Color(0xFF470832), Color(0xFF1C0021)), R.drawable.send_icon_dark4)
)

data class ColorTheme(
    val pairColors: Pair<Color, Color>,
    val sendIcon: Int
)