package com.igordudka.aichat.ui.theme

import androidx.compose.ui.graphics.Color
import com.igordudka.aichat.R

val lightColorThemes =
    listOf(ColorTheme(Pair(Color(0xFFFB8CDC), Color(0xFFC27CC8).copy(alpha = 0.92f)), R.drawable.send_icon_light1),
    ColorTheme(Pair(Color(0xFFFFF38B).copy(alpha = 0.88f), Color(0xFFFFA235).copy(alpha = 0.63f)), R.drawable.send_icon_light2),
    ColorTheme(Pair(Color(0xFFC1FFA4).copy(alpha = 0.47f), Color(0xFFA8FF80).copy(alpha = 0.58f)), R.drawable.send_icon_light3),
    ColorTheme(Pair(Color(0xFF7AD5DB), Color(0xFF757AFF).copy(alpha = 0.49f)), R.drawable.send_icon_light4)
    )

val darkColorThemes = listOf(
    ColorTheme(Pair(Color(0xFF040D5A).copy(alpha = 0.4f), Color(0xFF0D7FA4).copy(alpha = 0.4f)), R.drawable.send_icon_dark1),
    ColorTheme(Pair(Color(0xFF1FE000).copy(alpha = 0.3f), Color(0xFF086100).copy(alpha = 0.25f)), R.drawable.send_icon_dark2),
    ColorTheme(Pair(Color(0xFFAF000B).copy(alpha = 0.48f), Color(0xFF610112).copy(alpha = 0.44f)), R.drawable.send_icon_dark3),
    ColorTheme(Pair(Color(0xFFFF00A8).copy(alpha = 0.37f), Color(0xFF700083).copy(alpha = 0.26f)), R.drawable.send_icon_dark4)
)

data class ColorTheme(
    val pairColors: Pair<Color, Color>,
    val sendIcon: Int
)