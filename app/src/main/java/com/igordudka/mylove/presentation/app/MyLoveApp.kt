package com.igordudka.mylove.presentation.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.igordudka.mylove.presentation.auth.LoginScreen
import com.igordudka.mylove.presentation.auth.LoginViewModel
import com.igordudka.mylove.presentation.auth.SignUpScreen
import com.igordudka.mylove.presentation.home.HomeScreen
import com.igordudka.mylove.presentation.home.settings.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyLoveApp(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
){
    val navHostController = rememberNavController()
    val startRoute = if (loginViewModel.hasUser) AppNavRoutes.HOME.name
    else AppNavRoutes.LOGIN.name
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            NavHost(navController = navHostController, startDestination = startRoute){
                composable(AppNavRoutes.HOME.name){
                    settingsViewModel.isDarkTheme.collectAsState().value?.let { it1 -> HomeScreen(isDark = it1, goToLogin = {
                        navHostController.navigate(AppNavRoutes.LOGIN.name){
                            popUpTo(AppNavRoutes.LOGIN.name){
                                inclusive = true
                            }
                        }
                    }) }
                }
                composable(AppNavRoutes.LOGIN.name){
                    LoginScreen(goToHome = {
                        navHostController.navigate(AppNavRoutes.HOME.name){
                            popUpTo(AppNavRoutes.HOME.name){
                                inclusive = true
                            }
                        }
                    }) {
                        navHostController.navigate(AppNavRoutes.SIGNUP.name)
                    }
                }
                composable(AppNavRoutes.SIGNUP.name){
                    SignUpScreen(goToHome = { navHostController.navigate(AppNavRoutes.HOME.name){
                        popUpTo(AppNavRoutes.HOME.name){
                            inclusive = true
                        }
                    } }) {
                        navHostController.navigate(AppNavRoutes.LOGIN.name)
                    }
                }
            }
        }
}

enum class AppNavRoutes {
    HOME, LOGIN, SIGNUP
}