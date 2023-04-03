package com.igordudka.mylove.presentation.home

import android.os.Build
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.mylove.presentation.home.chat.ChatScreen
import com.igordudka.mylove.presentation.home.love.NotLoveScreen
import com.igordudka.mylove.presentation.home.settings.SettingsScreen
import com.igordudka.mylove.presentation.home.settings.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    goToLogin: () -> Unit,
    isDark: Boolean
) {

    var currentScreen by rememberSaveable {
        mutableStateOf("chat")
    }

    if (settingsViewModel.isLove.collectAsState().value == false) {
        NotLoveScreen(isDark = isDark)
    } else {
        Scaffold {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(it)
            ) {
                AnimatedVisibility (currentScreen == "chat", enter = fadeIn(), exit = ExitTransition.None) {
                    Column(Modifier.weight(1f)) {
                        ChatScreen(isDark = isDark,
                        goToSettings = {
                            currentScreen = "settings"
                        })
                    }
                }
                AnimatedVisibility(visible = currentScreen == "settings",
                    enter = slideInHorizontally(initialOffsetX = {300}), exit = ExitTransition.None){
                    Column(Modifier.weight(1f)) {
                        SettingsScreen(goToChat = {
                            currentScreen = "chat"
                        }, goToLogin = goToLogin)
                    }
                }
            }
        }
    }
}