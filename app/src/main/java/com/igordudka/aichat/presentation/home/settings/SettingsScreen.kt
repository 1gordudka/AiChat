package com.igordudka.aichat.presentation.home.settings

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.igordudka.aichat.R
import com.igordudka.aichat.data.database.ChatMessage
import com.igordudka.aichat.presentation.auth.LoginViewModel
import com.igordudka.aichat.presentation.home.chat.BotMessageCard
import com.igordudka.aichat.presentation.home.chat.UserMessageCard
import com.igordudka.aichat.ui.theme.darkColorThemes
import com.igordudka.aichat.ui.theme.extraPadding
import com.igordudka.aichat.ui.theme.fontSizes
import com.igordudka.aichat.ui.theme.lightColorThemes
import com.igordudka.aichat.ui.theme.standardPadding
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.template.NativeBannerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    isDark: Boolean,
    goToLogin: () -> Unit,
    goToChat: () -> Unit
) {


    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.background)
    val colorPalette = if (isDark) darkColorThemes else lightColorThemes
    val fontSize = settingsViewModel.fontSize.collectAsState().value

    Scaffold(topBar = {
        Box(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 10.dp)
                .height(70.dp)
                .background(MaterialTheme.colorScheme.background),) {
            IconButton(onClick = { goToChat() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(50.dp))
            }
            Text(text = stringResource(id = R.string.settings), style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center))
        }
    }) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)) {
            item{
                Spacer(modifier = Modifier.height(extraPadding))
                Column {
                    if (settingsViewModel.asSystem.collectAsState().value == false){
                        settingsViewModel.isDarkTheme.collectAsState().value?.let {
                            SettingsItemCard(
                                name = R.string.dark_theme,
                                icon = R.drawable.theme_icon,
                                checkedChange = {
                                    settingsViewModel.setDark(it)
                                },
                                isOn = it
                            )
                        }
                        Spacer(modifier = Modifier.height(standardPadding))
                    }
                    settingsViewModel.asSystem.collectAsState().value?.let {
                        SettingsItemCard(
                            name = R.string.as_system,
                            icon = R.drawable.theme_icon,
                            checkedChange = {
                                settingsViewModel.setSystem(it)
                            },
                            isOn = it
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
                                    style = MaterialTheme.typography.bodyMedium,
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
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Выбор темы", style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(20.dp), color = MaterialTheme.colorScheme.onBackground)
                LazyRow() {
                    items(
                        colorPalette
                    ){
                        Spacer(modifier = Modifier.width(10.dp))
                        Box{
                            if (settingsViewModel.colorTheme.collectAsState().value != null){
                                if (colorPalette.indexOf(it) == settingsViewModel.colorTheme.collectAsState().value){
                                    Row(
                                        Modifier
                                            .clip(RoundedCornerShape(25.dp))
                                            .background(
                                                MaterialTheme.colorScheme.onBackground
                                            )
                                            .size(115.dp)
                                            .align(Alignment.Center)) {}
                                }
                            }
                            Row(
                                Modifier
                                    .clip(RoundedCornerShape(25.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(
                                                it.pairColors.first,
                                                it.pairColors.second
                                            )
                                        )
                                    )
                                    .size(100.dp)
                                    .clickable {
                                        settingsViewModel.setColorTheme(colorPalette.indexOf(it))
                                    }
                                    .align(Alignment.Center)) {}
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Column(Modifier.padding(16.dp)) {
                    Text(text = "Размер шрифта", style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(4.dp), color = MaterialTheme.colorScheme.onBackground)
                    if (fontSize != null){
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = fontSizes[fontSize].toString(),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                        Slider(value = fontSize!!.toFloat(), onValueChange = {
                            settingsViewModel.changeFontSize(it.toInt())
                        }, steps = fontSizes.size - 2, valueRange = 0f..fontSizes.size.toFloat() - 1,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.onBackground,
                                disabledThumbColor = MaterialTheme.colorScheme.onBackground,
                                activeTickColor = MaterialTheme.colorScheme.surface,
                                inactiveTickColor = MaterialTheme.colorScheme.onSurface,
                                activeTrackColor = MaterialTheme.colorScheme.onBackground,
                                inactiveTrackColor = MaterialTheme.colorScheme.surface

                            ))
                        BotMessageCard(message = ChatMessage(message = "Так будет выглядеть " +
                                "сообщение бота", time = "9.41"), colorPalette = colorPalette)
                        UserMessageCard(message = ChatMessage(message = "Так будет выглядеть " +
                                "ваше сообщение", time = "9.41"))
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
                style = MaterialTheme.typography.bodyMedium,
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