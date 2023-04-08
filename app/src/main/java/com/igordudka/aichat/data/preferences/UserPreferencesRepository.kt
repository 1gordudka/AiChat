package com.igordudka.aichat.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object{
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val AS_SYSTEM = booleanPreferencesKey("as_system")
        val IS_LOVE = booleanPreferencesKey("is_love")
        val THEME = intPreferencesKey("Theme")
        val FONT_SIZE = intPreferencesKey("FontSize")
    }


    suspend fun saveThemePreference(isDark: Boolean){
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }
    suspend fun saveSystemPreference(asSystem: Boolean){
        dataStore.edit {
            it[AS_SYSTEM] = asSystem
        }
    }
    suspend fun changeTheme(value: Int){
        dataStore.edit {
            it[THEME] = value
        }
    }
    suspend fun changeFont(value: Int){
        dataStore.edit {
            it[FONT_SIZE] = value
        }
    }
    val isDarkTheme: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME] ?: false
    }
    val asSystem: Flow<Boolean> = dataStore.data.map {
        it[AS_SYSTEM] ?: true
    }
    val theme: Flow<Int> = dataStore.data.map {
        it[THEME] ?: 0
    }
    val fontSize: Flow<Int> = dataStore.data.map {
        it[FONT_SIZE] ?: 2
    }

}