package com.igordudka.aichat.di

import android.app.Application
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AiChatApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        Firebase.database.setPersistenceEnabled(true)
        MobileAds.initialize(
            this
        ) { Log.d("YandexMobileAds", "SDK initialized") }
    }
}