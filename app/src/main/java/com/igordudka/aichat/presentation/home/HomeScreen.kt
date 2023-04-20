package com.igordudka.aichat.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.aichat.presentation.home.chat.ChatScreen
import com.igordudka.aichat.presentation.home.settings.SettingsScreen
import com.igordudka.aichat.presentation.home.settings.SettingsViewModel
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    goToLogin: () -> Unit,
    isDark: Boolean
) {

    var currentScreen by rememberSaveable {
        mutableStateOf("chat")
    }
    val context = LocalContext.current


            Column(
                Modifier
                    .fillMaxSize()
            ) {
                AnimatedVisibility (currentScreen == "chat", enter = fadeIn(), exit = ExitTransition.None) {
                    Column(Modifier.fillMaxSize()) {
                        ChatScreen(isDark = isDark,
                        goToSettings = {
                            currentScreen = "settings"
                        })
                    }
                }
                AnimatedVisibility(visible = currentScreen == "settings",
                    enter = slideInHorizontally(initialOffsetX = {300}), exit = ExitTransition.None){
                    Column(Modifier.fillMaxSize()) {
                        SettingsScreen(goToChat = {
                            currentScreen = "chat"
                        }, goToLogin = goToLogin, isDark = isDark)
                    }
                }
            }
    }