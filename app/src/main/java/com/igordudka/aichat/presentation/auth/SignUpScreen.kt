package com.igordudka.aichat.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.igordudka.aichat.presentation.home.settings.SettingsViewModel
import com.igordudka.aichat.ui.theme.darkColorThemes
import com.igordudka.aichat.ui.theme.lightColorThemes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    goToHome: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    goToLogin: () -> Unit
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.background)
    val loginUiState = loginViewModel.loginUiState
    val colorPalette = if (settingsViewModel.isDarkTheme.collectAsState().value == true) darkColorThemes else lightColorThemes
    val isError = loginUiState.signUpError != null
    val context = LocalContext.current


    LaunchedEffect(key1 = loginViewModel.hasUser){
        if (loginViewModel.hasUser){
            goToHome.invoke()
        }
    }

    if (settingsViewModel.colorTheme.collectAsState().value != null){
        Column(
            Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
            ){
                Column(
                    Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].pairColors.first,
                                    colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].pairColors.second
                                )
                            )
                        )
                        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "регистрация", color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 20.dp))
                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedTextField(value = loginUiState.userNameSignup, onValueChange = {loginViewModel.onUserNameChangeSignup(it)},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground
                        ),
                        label = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) },
                        placeholder = { Text(text = "Email", color = MaterialTheme.colorScheme.onBackground) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(value = loginUiState.passwordSignUp, onValueChange = {loginViewModel.onPasswordChangeSignup(it)},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground
                        ),
                        label = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                        placeholder = { Text(text = "Пароль", color = MaterialTheme.colorScheme.onBackground) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(value = loginUiState.confirmPasswordSignUp, onValueChange = {loginViewModel.onConfirmPasswordChange(it)},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground
                        ),
                        label = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
                        placeholder = { Text(text = "Повтори пароль", color = MaterialTheme.colorScheme.onBackground) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedButton(onClick = {
                        loginViewModel.createUser(context)
                    }, border = BorderStroke(width = 2.dp, Brush.horizontalGradient(
                        listOf(colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].pairColors.first,
                            colorPalette[settingsViewModel.colorTheme.collectAsState().value!!].pairColors.second)
                    ))) {
                        Text(text = "стать частью", style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "уже зареган?", modifier = Modifier
                        .clickable {
                            goToLogin()
                        }
                        .padding(16.dp), style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }

}