package com.igordudka.mylove.presentation


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.igordudka.mylove.presentation.app.MyLoveApp
import com.igordudka.mylove.presentation.home.settings.SettingsViewModel
import com.igordudka.mylove.ui.theme.MyLoveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Firebase.initialize(this)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                settingsViewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                MyLoveTheme {
                    MyLoveApp()
                }
            }
        }
    }
}