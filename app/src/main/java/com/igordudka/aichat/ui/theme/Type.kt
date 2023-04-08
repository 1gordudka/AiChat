package com.igordudka.aichat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.igordudka.aichat.R
import com.igordudka.aichat.presentation.home.settings.SettingsViewModel
import javax.inject.Inject


val Typography = Typography(
    // Title
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_semibold)),
        fontSize = 35.sp
    ),
    // Message
displayMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.inter_medium)),
    fontSize = 15.sp
),
    // TextField
displayLarge = TextStyle(
    fontFamily = FontFamily(Font(R.font.inter_semibold)),
    fontSize = 16.sp
),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_semibold)),
        fontSize = 13.sp
    ),
    // Setting
bodyMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.inter_semibold)),
    fontSize = 19.sp
)
)