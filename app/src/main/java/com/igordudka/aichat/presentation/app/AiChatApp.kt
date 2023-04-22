package com.igordudka.aichat.presentation.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.igordudka.aichat.presentation.home.HomeScreen
import com.igordudka.aichat.presentation.home.settings.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AiChatApp(
    settingsViewModel: SettingsViewModel = hiltViewModel()
){
    val navHostController = rememberNavController()
    val startRoute = AppNavRoutes.HOME.name

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            NavHost(navController = navHostController, startDestination = startRoute){
                composable(AppNavRoutes.HOME.name){
                    settingsViewModel.isDarkTheme.collectAsState().value?.let { it1 -> HomeScreen(isDark = if (settingsViewModel
                            .asSystem.collectAsState().value == true
                    ) isSystemInDarkTheme() else it1) }
                }
            }
        }
}

enum class AppNavRoutes {
    HOME
}