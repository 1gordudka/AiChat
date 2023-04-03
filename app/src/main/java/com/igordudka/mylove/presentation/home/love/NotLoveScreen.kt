package com.igordudka.mylove.presentation.home.love

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.mylove.presentation.home.settings.SettingsItemCard
import com.igordudka.mylove.presentation.home.settings.SettingsViewModel
import com.igordudka.mylove.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotLoveScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    isDark: Boolean
){

    Scaffold {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Что за фигня, я не понял????", style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(10.dp))
            settingsViewModel.isLove.collectAsState().value?.let {
                SettingsItemCard(
                    name = R.string.love,
                    icon = R.drawable.heart_icon,
                    checkedChange = {
                        settingsViewModel.setLove(!settingsViewModel.isLove.value!!)},
                    isOn = it
                )
            }
        }
    }
}