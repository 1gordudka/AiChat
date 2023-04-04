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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.aichat.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    goToHome: () -> Unit,
    goToSignUp: () -> Unit
) {

    val loginUiState = loginViewModel.loginUiState
    val context = LocalContext.current
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = loginViewModel.hasUser){
        if (loginViewModel.hasUser){
            goToHome.invoke()
        }
    }

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
                                MaterialTheme.colorScheme.onTertiary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    )
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "вход", color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(top = 20.dp))
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(value = loginUiState.userName, onValueChange = {loginViewModel.onUserNameChange(it)},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedLabelColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.background
                ),
                label = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null)},
                placeholder = { Text(text = "Email", color = MaterialTheme.colorScheme.surface)},
                singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(value = loginUiState.password, onValueChange = {loginViewModel.onPasswordNameChange(it)},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedLabelColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.background
                    ),
                    label = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null)},
                    placeholder = { Text(text = "Пароль", color = MaterialTheme.colorScheme.surface)},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible },) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedButton(onClick = {
                    loginViewModel.loginUser(context = context)
                }, border = BorderStroke(width = 2.dp, Brush.horizontalGradient(listOf(
                    MaterialTheme.colorScheme.onTertiary, MaterialTheme.colorScheme.tertiary
                )))) {
                    Text(text = "войти", style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.background)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "еще не с нами?", modifier = Modifier
                    .clickable {
                        goToSignUp()
                    }
                    .padding(16.dp), style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background)
            }
        }
    }
}
