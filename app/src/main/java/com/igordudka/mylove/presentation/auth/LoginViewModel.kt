package com.igordudka.mylove.presentation.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.mylove.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    val currentUser = authRepository.currentUser
    val hasUser: Boolean
        get() = authRepository.hasUser()
    var loginUiState by mutableStateOf(LoginUiState())
        private set
    fun onUserNameChange(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }
    fun onPasswordNameChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }
    fun onUserNameChangeSignup(userName: String){
        loginUiState = loginUiState.copy(userNameSignup = userName)
    }
    fun onPasswordChangeSignup(passwordSignUp: String){
        loginUiState = loginUiState.copy(passwordSignUp = passwordSignUp)
    }
    fun onConfirmPasswordChange(password: String){
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }


    private fun validateLoginForm() =
        loginUiState.userName.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignupForm() =
        loginUiState.userNameSignup.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignupForm()){
                throw IllegalArgumentException("Email и пароль не могут быть пустыми!")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if (loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp){
                throw IllegalArgumentException("Пароли не совпадают!")
            }
            loginUiState = loginUiState.copy(signUpError = null)
            authRepository.createUser(
                loginUiState.userNameSignup,
                loginUiState.passwordSignUp
            ){isSucceful ->
                if (isSucceful){
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(context, "Ошибка!", Toast.LENGTH_LONG).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }

        }catch (e: Exception){
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
            Toast.makeText(context, loginUiState.signUpError, Toast.LENGTH_LONG).show()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()){
                throw IllegalArgumentException("Email и пароль не могут быть пустыми!")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            authRepository.login(
                loginUiState.userName,
                loginUiState.password
            ){isSuccessful ->
                loginUiState = if (isSuccessful){
                    loginUiState.copy(isSuccessLogin = true)
                }else{
                    loginUiState.copy(isSuccessLogin = false)
                }
            }

        }catch (e: Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
            Toast.makeText(context, loginUiState.loginError, Toast.LENGTH_SHORT).show()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun signout() = authRepository.logout()

}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSignup: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)