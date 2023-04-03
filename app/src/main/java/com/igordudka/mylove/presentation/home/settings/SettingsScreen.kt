package com.igordudka.mylove.presentation.home.settings

import android.inputmethodservice.KeyboardView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.mylove.ui.theme.*
import com.igordudka.mylove.R
import com.igordudka.mylove.presentation.auth.LoginViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    goToLogin: () -> Unit,
    goToChat: () -> Unit
) {


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)) {
            IconButton(onClick = { goToChat() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(50.dp))
            }
            Text(text = stringResource(id = R.string.settings), style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center))
        }
        Spacer(modifier = Modifier.height(extraPadding))
        Column {
            settingsViewModel.isLove.collectAsState().value?.let { it ->
                SettingsItemCard(
                    name = R.string.love,
                    icon = R.drawable.heart_icon,
                    checkedChange = { settingsViewModel.setLove(it) },
                    isOn = it
                )
            }
            Spacer(Modifier.height(standardPadding))
            settingsViewModel.isDarkTheme.collectAsState().value?.let {
                SettingsItemCard(
                    name = R.string.dark_theme,
                    icon = R.drawable.theme_icon,
                    checkedChange = {
                        settingsViewModel.setDark(it)
                                    },
                    isOn = it || isSystemInDarkTheme()
                )
            }
            Spacer(modifier = Modifier.height(standardPadding))
            Card(modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 75.dp)
                .padding(extraPadding)
                .clickable {
                    loginViewModel.signout()
                    goToLogin()
                },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor =
                MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    Modifier
                        .sizeIn(minHeight = 75.dp)
                        .fillMaxWidth()
                        .padding(extraPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.logout),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = null,
                            Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SettingsItemCard(
    name: Int,
    icon: Int,
    checkedChange: (Boolean) -> Unit,
    isOn: Boolean
) {
    
    Card(modifier = Modifier
        .fillMaxWidth()
        .sizeIn(minHeight = 75.dp)
        .padding(extraPadding),
    shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor =
        MaterialTheme.colorScheme.surface)
    ) {
        Row(
            Modifier
                .sizeIn(minHeight = 75.dp)
                .fillMaxWidth()
                .padding(extraPadding),
        verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = name),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.width(10.dp))
                Image(imageVector = ImageVector.vectorResource(id = icon), contentDescription = null)
            }
            androidx.compose.material3.Switch(checked = isOn, onCheckedChange = { checkedChange(!isOn) },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colorScheme.background,
                checkedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedBorderColor = MaterialTheme.colorScheme.onBackground,
                checkedBorderColor = MaterialTheme.colorScheme.onBackground,
                uncheckedTrackColor = MaterialTheme.colorScheme.onBackground,
                checkedTrackColor = MaterialTheme.colorScheme.onBackground
            ))
        }
    }
}