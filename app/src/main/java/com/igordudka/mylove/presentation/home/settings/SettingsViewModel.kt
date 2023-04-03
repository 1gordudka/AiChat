package com.igordudka.mylove.presentation.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.mylove.data.preferences.UserPreferencesRepository
import com.igordudka.mylove.presentation.app.AppNavRoutes
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

    var isDarkTheme = userPreferencesRepository.isDarkTheme.map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )
    var isLove = userPreferencesRepository.isLove.map { it }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )
    fun setDark(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.saveThemePreference(value)
        }
    }
    fun setLove(value: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.saveLovePreference(value)
        }
    }
}