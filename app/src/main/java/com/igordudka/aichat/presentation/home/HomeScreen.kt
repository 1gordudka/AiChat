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
    val context = LocalContext.current
    val mInterstitialAd = InterstitialAd(context)
    mInterstitialAd.setAdUnitId("R-M-2302601-1")
    val adRequest = AdRequest.Builder().build()
    mInterstitialAd.setInterstitialAdEventListener(object : InterstitialAdEventListener {
        override fun onAdLoaded() {
            mInterstitialAd.show()
        }
        override fun onAdFailedToLoad(p0: AdRequestError) {
            Log.w("FAIL", p0.toString())
        }
        override fun onAdShown() {

        }
        override fun onAdDismissed() {

        }
        override fun onAdClicked() {

        }
        override fun onLeftApplication() {

        }
        override fun onReturnedToApplication() {

        }
        override fun onImpression(p0: ImpressionData?) {

        }

    })


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
                            mInterstitialAd.loadAd(adRequest)
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