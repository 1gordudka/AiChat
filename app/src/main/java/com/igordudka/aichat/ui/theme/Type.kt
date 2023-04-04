package com.igordudka.aichat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.igordudka.aichat.R

val Typography = Typography(
    // Title
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontSize = 35.sp
    ),
    // Message
displayMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.inter_semibold)),
    fontSize = 17.sp
),
    // TextField
displayLarge = TextStyle(
    fontFamily = FontFamily(Font(R.font.inter_semibold)),
    fontSize = 19.sp
),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_semibold)),
        fontSize = 14.sp
    )
)