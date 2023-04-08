package com.igordudka.aichat.presentation.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.aichat.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
        }
    }

    val fontSize = userPreferencesRepository.fontSize.map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )
    var isDarkTheme = userPreferencesRepository.isDarkTheme.map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )
    val asSystem = userPreferencesRepository.asSystem.map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )
    val colorTheme = userPreferencesRepository.theme.map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )
    fun changeFontSize(value: Int){
        viewModelScope.launch {
            userPreferencesRepository.changeFont(value)
        }
    }
    fun setColorTheme(value: Int){
        viewModelScope.launch {
            userPreferencesRepository.changeTheme(value)
        }
    }
    fun setDark(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.saveThemePreference(value)
        }
    }
    fun setSystem(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.saveSystemPreference(value)
        }
    }
}